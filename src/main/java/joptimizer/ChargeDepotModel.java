package joptimizer;

import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import lombok.Builder;

import java.util.Arrays;
import java.util.stream.DoubleStream;

@Builder
public class ChargeDepotModel {
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
       double[] kVector= DoubleStream.of(kList).map(x -> -x).toArray();
       return new PDQuadraticMultivariateRealFunction(pMatrix, kVector, 0);
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
        var inequalities = new ConvexMultivariateRealFunction[1];
        inequalities[0]=SumConstraint.builder().nDim(nDim).ub(pDepotMax).build();
        return inequalities;
    }

}
