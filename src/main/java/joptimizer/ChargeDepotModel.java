package joptimizer;

import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import lombok.Builder;
import org.apache.commons.lang3.ArrayUtils;

import java.util.stream.DoubleStream;

@Builder
public class ChargeDepotModel {
    public static final double POWER_SMALL = 1;

    @Builder.Default
    boolean isCharging=true;
    double[] kList;
    double[] qList;
    double[] pMaxList;
    double[] pMinList;
    double[] socList;
    double pDepotMax;
    double socMax;


    public ConvexMultivariateRealFunction costFunction() {

        double[][] pMatrix = new double[kList.length][kList.length];
        for (int i = 0; i < pMatrix.length && i < qList.length; i++) {
            pMatrix[i][i] = qList[i];
        }
       double[] kVector= DoubleStream.of(kList).map(x -> isCharging?-x:x).toArray();
       return new PDQuadraticMultivariateRealFunction(pMatrix, kVector, 0);
    }

    public ConvexMultivariateRealFunction[] constraints() {
        return  isCharging
                ? ArrayUtils.addAll(zeroPowerHighSoc(),ArrayUtils.addAll(bounds(),powerTotal()))
                : ArrayUtils.addAll(bounds(),powerTotal());

    }

    public ConvexMultivariateRealFunction[] bounds() {
        int nDim= pMaxList.length;

        int j=0;
        var inequalities = new ConvexMultivariateRealFunction[2*nDim];
        for (int i = 0; i < nDim ; i++) {
            inequalities[j] = UpperBoundConstraint.builder().nDim(nDim).idxVariable(i).ub(pMaxList[i]).build();
            j++;
        }
        for (int i = 0; i < nDim ; i++) {
            inequalities[j] = LowerBoundConstraint.builder().nDim(nDim).idxVariable(i).lb(pMinList[i]).build();
            j++;
        }

        return inequalities;
    }

    public ConvexMultivariateRealFunction[] powerTotal() {
        int nDim= pMaxList.length;
        var inequalities = new ConvexMultivariateRealFunction[2];
        inequalities[0]=SumConstraint.builder().isSumMaxLimited(true).nDim(nDim).limit(pDepotMax).build();
        inequalities[1]=SumConstraint.builder().isSumMaxLimited(false).nDim(nDim).limit(pDepotMax).build();

        return inequalities;
    }

    public ConvexMultivariateRealFunction[] zeroPowerHighSoc() {
        int nDim= pMaxList.length;

        var inequalities = new ConvexMultivariateRealFunction[nDim];
        for (int i = 0; i < nDim ; i++) {
            double ub=socList[i]<socMax ? pMaxList[i]: POWER_SMALL;
            inequalities[i] = UpperBoundConstraint.builder().nDim(nDim).idxVariable(i).ub(ub).build();
        }

        return inequalities;
    }


}
