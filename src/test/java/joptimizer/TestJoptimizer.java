package joptimizer;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.optimizers.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import optimization_apache.depot_charging.DepotModel;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

@Log
public class TestJoptimizer {
    public static final double Q = 1e-2;
    ChargeDepotModel model;
    JOptimizer optimizerJo;

    @SneakyThrows
    @Before
    public void init() {
        model = ChargeDepotModel.builder()
                .kList(new double[]{1, 1, 0.7})
                .qList(new double[]{Q,Q,Q})
                .pMaxList(new double[]{1000, 1000, 1000})
                .pMinList(new double[]{-100, -100, -100})
                .socList(new double[]{1, 1, 1})
                .pDepotMax(100)
                .socMax(1)
                .build();
        optimizerJo = new JOptimizer();
    }


    @SneakyThrows
    @Test
    public void when() {
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(model.costFunction()); // Set the objective function
        or.setFi(ArrayUtils.addAll(model.bounds(),model.powerTotal()));
        or.setInitialPoint(new double[]{0.4, 0.4,0.4}); // Optional: initial guess
        or.setToleranceFeas(1.E-0); // Tolerance on feasibility
        or.setTolerance(1e-0); // Tolerance on optimization

//        NewtonLEConstrainedFSP  optimizer = new NewtonLEConstrainedFSP();  //todo fix hession

        //var bf=new BarrierFunction();
        // var optimizer = new com.joptimizer.optimizers.BarrierMethod();

        var optimizer=new JOptimizer();
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





