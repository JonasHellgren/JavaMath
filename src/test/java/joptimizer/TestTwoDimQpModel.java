package joptimizer;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTwoDimQpModel {

    ConvexMultivariateRealFunction objectiveFunction;
    @BeforeEach
    void init() {
        double[][] Q = {{2, 0}, {0, 2}};
        double[] c = {-4, -4};
        // Objective function: 0.5 * x^T * Q * x + c^T * x
        objectiveFunction = new ConvexMultivariateRealFunction() {
            @Override
            public double value(DoubleMatrix1D dm) {
                // Manual calculation of the objective value
                var X=dm.toArray();
                return 0.5 * (X[0] * X[0] * Q[0][0] + X[1] * X[1] * Q[1][1]) + c[0] * X[0] + c[1] * X[1];
            }

            @Override
            public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
                // Manual calculation of the gradient
                var X=dm.toArray();
                return new DenseDoubleMatrix1D(new double[]{2 * X[0] - 4, 2 * X[1] - 4});
            }

            @Override
            public DoubleMatrix2D hessian(DoubleMatrix1D X) {
                // For this example, the Hessian is constant and equals Q
                return new DenseDoubleMatrix2D(Q);
            }

            @Override
            public int getDim() {
                return 2;
            }
        };
    }

    @SneakyThrows
    @Test
    void when() {

        var or = new OptimizationRequest();
        or.setF0(objectiveFunction);
        or.setInitialPoint(new double[]{0, 0}); // Initial guess
        or.setTolerance(1.E-9); // Tolerance

        // Create and run the optimizer
        JOptimizer optimizer = new JOptimizer();
        optimizer.setOptimizationRequest(or);
        optimizer.optimize();

        double[] solution = optimizer.getOptimizationResponse().getSolution();
        System.out.println("Solution: x1 = " + solution[0] + ", x2 = " + solution[1]);



    }
}
