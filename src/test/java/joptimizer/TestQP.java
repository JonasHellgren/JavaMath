package joptimizer;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.OptimizationResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
public class TestQP {


    public static final double TOL = 1e-5;

    @SneakyThrows
    public static void main(String[] args) {
        /**
         * The basic PhaseI problem relative to testQCQuadraticProgramming2DNoInitialPoint.
         * min(s) s.t.
         * (x+2)^2 + (y+2)^2 -1.75 < s
         * This problem can't be solved without an initial point, because the relative PhaseI problem
         * min(r) s.t.
         * (x+2)^2 + (y+2)^2 -1.75 -s < r
         * is unbounded.
         */
            log.info("testQCQuadraticProgramming2DNoInitialPointPhaseI");

            // Objective function
            double[] f0 = new double[]{0, 0, 1};//s
            LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(f0, 0);

            //inequalities
            ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[1];
            inequalities[0] = new ConvexMultivariateRealFunction() {

                @Override
                public double value(DoubleMatrix1D dm) {
                    var X=dm.toArray();
                    double x = X[0];
                    double y = X[1];
                    double s = X[2];
                    return Math.pow(x+2, 2)+Math.pow(y+2, 2)-1.75-s;
                }

                @Override
                public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
                    var X=dm.toArray();
                    double x = X[0];
                    double y = X[1];
                    return new DenseDoubleMatrix1D(new double[]{2*(x+2), 2*(y+2), -1 });
                }

                @Override
                public DoubleMatrix2D hessian(DoubleMatrix1D dm) {
                    var X=dm.toArray();
                    double[][] ret = new double[3][3];
                    ret[0][0] = 2;
                    ret[1][1] = 2;
                    return new DenseDoubleMatrix2D(ret);
                }

                @Override
                public int getDim() {
                    return 3;
                }
            };

            //optimization problem
            OptimizationRequest or = new OptimizationRequest();
            or.setF0(objectiveFunction);
            //or.setInitialPoint(new double[] {0.5,0.5,94375.0});
            or.setInitialPoint(new double[] {-2, -2, 10});
            or.setFi(inequalities);
            or.setCheckKKTSolutionAccuracy(true);

            //optimization
            JOptimizer opt = new JOptimizer();
            opt.setOptimizationRequest(or);
            opt.optimize();


            OptimizationResponse response = opt.getOptimizationResponse();
            double[] sol = response.getSolution();
            log.info("sol   : " + ArrayUtils.toString(sol));
            log.info("value : " + objectiveFunction.value(new DenseDoubleMatrix1D(sol)));
            assertEquals(-2.  , sol[0], TOL);
            assertEquals(-2.  , sol[1], TOL);
            assertEquals(-1.75, sol[2], TOL);
        }

}
