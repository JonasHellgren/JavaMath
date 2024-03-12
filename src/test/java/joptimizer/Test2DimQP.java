package joptimizer;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * The basic PhaseI problem relative to testQCQuadraticProgramming2DNoInitialPoint.
 * f=1/2*xQx'+c'x
 * min(f) s.t.
 * x[0]<ub
 */


@Log
public class Test2DimQP {
    public static final double TOL = 1e-5;

    @SneakyThrows
    public static void main(String[] args) {
        double[][] Q = {{2, 0}, {0, 2}};
        double[] c = {-4, -4};
        var objectiveFunction = new ConvexMultivariateRealFunction() {
            @Override
            public double value(DoubleMatrix1D dm) {
                var X=dm.toArray();
                return 0.5 * (X[0] * X[0] * Q[0][0] + X[1] * X[1] * Q[1][1]) + c[0] * X[0] + c[1] * X[1];
            }

            @Override
            public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
                var X=dm.toArray();
                return DoubleFactory1D.dense.make(new double[]{2 * X[0] - 4, 2 * X[1] - 4});
            }

            @Override
            public DoubleMatrix2D hessian(DoubleMatrix1D X) {
                return DoubleFactory2D.dense.make(Q); //
            }

            @Override
            public int getDim() {
                return 2;
            }
        };

        var inequalities = new ConvexMultivariateRealFunction[1];
        var upperBoundConstraint = UpperBoundConstraint.builder().nDim(2).idxVariable(0).ub(1d).build();
        inequalities[0]= upperBoundConstraint;

        //optimization
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[]{-2, -2});
        or.setFi(inequalities);  //exception if not defined
        or.setCheckKKTSolutionAccuracy(true);
        or.setTolerance(1e-5);  //affect nof iter
        JOptimizer opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        opt.optimize();

        var response = opt.getOptimizationResponse();

        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));
        log.info("value : " + objectiveFunction.value(new DenseDoubleMatrix1D(sol)));
        log.info("nIter="+upperBoundConstraint.nIter);
        assertEquals(1d, sol[0], TOL);
        assertEquals(2d, sol[1], TOL);
    }



}
