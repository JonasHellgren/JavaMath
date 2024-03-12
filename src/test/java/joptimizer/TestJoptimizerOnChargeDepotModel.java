package joptimizer;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.joptimizer.optimizers.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

@Log
public class TestJoptimizerOnChargeDepotModel {
    public static final double Q = 1e-5;
    ChargeDepotModel model;

    @SneakyThrows
    @Before
    public void init() {
        model = ChargeDepotModel.builder()
                .kList(new double[]{0.2, 0.6, 0.2})
                .qList(new double[]{Q, Q, Q})
                .pMaxList(new double[]{1000, 1000, 1000})
                .pMinList(new double[]{-1, -1, -1})
                .socList(new double[]{0.5, 0.5, 0.99})
                .pDepotMax(1200)
                .socMax(0.98)
                .build();
    }


    @SneakyThrows
    @Test
    public void when() {
        OptimizationRequest or = new OptimizationRequest();
        or.setMaxIteration(1_000);
        or.setF0(model.costFunction()); // Set the objective function
        or.setFi(model.constraints());
        or.setInitialPoint(new double[]{0, 0, 0}); // Optional: initial guess
        or.setToleranceFeas(1e-5); // Tolerance on feasibility
        or.setTolerance(1e1); // Tolerance on optimization

        var optimizer = new JOptimizer();
        optimizer.setOptimizationRequest(or);
        optimizer.optimize();
        var response = optimizer.getOptimizationResponse();
        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));

    }

    @NotNull
    private static DenseDoubleMatrix1D getM(double[] x) {
        return new DenseDoubleMatrix1D(x);
    }


}





