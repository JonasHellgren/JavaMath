package joptimizer;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.joptimizer.exception.JOptimizerException;
import com.joptimizer.optimizers.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

@Log
public class TestJoptimizerOnChargeDepotModel {
    public static final double Q = 1e-5;
    public static final int TOL = 10;
    ChargeDepotModel model;

    @SneakyThrows
    @Before
    public void init() {
        model = ChargeDepotModel.builder()
                .isCharging(true)
                .kList(new double[]{0.2, 0.6, 0.2})  //model handles sign logik
                .qList(new double[]{Q, Q, Q})
                .pMaxList(new double[]{1000, 1000, 1000})
                .pMinList(new double[]{0, 0, 0})
                .socList(new double[]{0.5, 0.5, 0.99}).socMax(0.98)
                .pDepotMax(1200)
                .build();
    }


    @SneakyThrows
    @Test
    public void whenCharging_thenCorrect() {
        var response = getOptimizationResponse(new double[]{0.1,0.1,0.1});
        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));
        Assertions.assertEquals(model.pDepotMax, Arrays.stream(sol).sum(), TOL);
        Assertions.assertTrue(sol[0]<sol[1]);
        Assertions.assertEquals(0,sol[2], TOL);
    }

    @SneakyThrows
    @Test
    public void whenDisCharging_thenCorrect() {
        model = ChargeDepotModel.builder()
                .isCharging(false)
                .kList(new double[]{0.2, 0.6, 0.9})
                .qList(new double[]{Q, Q, Q})
                .pMaxList(new double[]{0, 0, 0})
                .pMinList(new double[]{-1000, -1000, -1000})
                .socList(new double[]{0.5, 0.5, 0.99}).socMax(0.98)
                .pDepotMax(1200)
                .build();

        var response = getOptimizationResponse(new double[]{-1, -1, -1});
        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));
        Assertions.assertEquals(-model.pDepotMax, Arrays.stream(sol).sum(), TOL);
        Assertions.assertTrue(Math.abs(sol[0])<Math.abs(sol[1]));
        Assertions.assertNotEquals(0,sol[2], TOL);  //deactivated if not charging

    }


    private OptimizationResponse getOptimizationResponse(double[] initialPoint) throws JOptimizerException {
        OptimizationRequest or = new OptimizationRequest();
        or.setMaxIteration(1_000);
        or.setF0(model.costFunction()); // Set the objective function
        or.setFi(model.constraints());
        or.setInitialPoint(initialPoint); // Optional: initial guess
        or.setToleranceFeas(1e-5); // Tolerance on feasibility
        or.setTolerance(1e1); // Tolerance on optimization

        var optimizer = new JOptimizer();
        optimizer.setOptimizationRequest(or);
        optimizer.optimize();
        return optimizer.getOptimizationResponse();
    }


    @NotNull
    private static DenseDoubleMatrix1D getM(double[] x) {
        return new DenseDoubleMatrix1D(x);
    }


}





