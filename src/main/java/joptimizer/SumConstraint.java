package joptimizer;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

@Builder
public class SumConstraint implements ConvexMultivariateRealFunction {

    @NonNull Integer nDim;
    @NonNull Double ub;

    @Builder.Default
    @Getter  int nIter =0;

    @Override
    public double value(DoubleMatrix1D dm) {
        nIter++;
        return Arrays.stream(dm.toArray()).sum()-ub;
    }

    @Override
    public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
        double[] arr= new double[nDim];
        Arrays.fill(arr, 1);
        return DoubleFactory1D.dense.make(arr);
    }

    @Override
    public DoubleMatrix2D hessian(DoubleMatrix1D dm) {
        return  new DenseDoubleMatrix2D(new double[nDim][nDim]);
    }

    @Override
    public int getDim() {
        return nDim;
    }
}
