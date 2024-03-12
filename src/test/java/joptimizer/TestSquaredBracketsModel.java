package joptimizer;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.optimizers.NewtonLEConstrainedFSP;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.OptimizationResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Log
class TestSquaredBracketsModel {

    SquaredBracketsModel model;
    ConvexMultivariateRealFunction objectiveFunction;

    @BeforeEach
    void init() {
        model=new SquaredBracketsModel();

        objectiveFunction = new ConvexMultivariateRealFunction() {
            @Override
            public double value(DoubleMatrix1D dm) {
                return model.calculateObjectiveValue(dm.toArray());
            }

            @Override
            public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
                return  new DenseDoubleMatrix1D(model.calculateObjectiveGradient(dm.toArray()));
            }

            @Override
            public DoubleMatrix2D hessian(DoubleMatrix1D doubleMatrix1D) {
                DoubleFactory2D F2 = DoubleFactory2D.dense;
                return F2.make(new double[][] {
                        { 0.0, 0.0},
                        { 0.0, 0}});
            }

            @Override
            public int getDim() {
                return 2;        }
        };

    }


    @SneakyThrows
    @Test
    void when() {
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction); // Set the objective function
        or.setInitialPoint(new double[]{0.4, 0.4,0.4}); // Optional: initial guess
        or.setToleranceFeas(1.E-1); // Tolerance on feasibility
        or.setTolerance(1.E-1); // Tolerance on optimization

        NewtonLEConstrainedFSP optimizer = new NewtonLEConstrainedFSP();  //todo fix hession

        //var bf=new BarrierFunction();
        // var optimizer = new com.joptimizer.optimizers.BarrierMethod();

        optimizer.setOptimizationRequest(or);
        optimizer.optimize();

        OptimizationResponse response = optimizer.getOptimizationResponse();
        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));

    }


}
