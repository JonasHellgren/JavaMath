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
    public static final double REL_TRES_HOLD = Double.MIN_VALUE;
    public static final double ABS_TRES_HOLD = 1e-10;

    public static final double BARRIER_WEIGHT = 0*1e2;
    public static final double TOL = 1e-4;
    public static final int EVAL_MAX = 100_000;
    public static final double[] INITIAL_GUESS = {0, 0, 0};
    public static final int MAX_ITERATIONS = 100_000;
    public static final double COST_TOL = 0.3;
    public static final double EXPECTED_COST = -0.5 * 2 - 0.5 * 3;
    DepotModel model;
    JOptimizer optimizerJo;

    @SneakyThrows
    @Before
    public void init() {
        model = DepotModel.builder()
                .kList(new double[]{1, 2, 3})
                .pMaxList(new double[]{0.5, 0.5, 0.5})
                .socList(new double[]{1, 1, 1})
                .pDepotMax(1)
                .barrierWeight(BARRIER_WEIGHT)
                .barrierWeightQuad(BARRIER_WEIGHT)
                .socMax(1)
                .build();
        optimizerJo = new JOptimizer();


    }

    ConvexMultivariateRealFunction objectiveFunction = new ConvexMultivariateRealFunction() {
        @Override
        public double value(DoubleMatrix1D dm) {
            //return model2.calculateObjectiveValue(dm.toArray());
            return model.getObjectiveFunction().getObjectiveFunction().value(dm.toArray());
        }

        @Override
        public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
            //return getM(model2.calculateObjectiveGradient(dm.toArray()));
            //return null;
            return  new DenseDoubleMatrix1D(
                    model.getFiniteDiffGradient().getObjectiveFunctionGradient().value(dm.toArray()));
        }

        @Override
        public DoubleMatrix2D hessian(DoubleMatrix1D doubleMatrix1D) {
            DoubleFactory2D F2 = DoubleFactory2D.dense;
            return F2.make(new double[][] {
                    { 0.0, 0.0, 0.0},
                    { 0.0, 0, 0.0 },
                    { 0.0, 0.0, 0}});
        }

        @Override
        public int getDim() {
            return 3;
        }
    };


    @SneakyThrows
    @Test
    public void when() {
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction); // Set the objective function
        or.setInitialPoint(new double[]{0.4, 0.4,0.4}); // Optional: initial guess
        or.setToleranceFeas(1.E-1); // Tolerance on feasibility
        or.setTolerance(1.E-1); // Tolerance on optimization

        NewtonLEConstrainedFSP  optimizer = new NewtonLEConstrainedFSP();  //todo fix hession

        //var bf=new BarrierFunction();
        // var optimizer = new com.joptimizer.optimizers.BarrierMethod();

        optimizer.setOptimizationRequest(or);
        optimizer.optimize();

        OptimizationResponse response = optimizer.getOptimizationResponse();
        double[] sol = response.getSolution();
        log.info("sol   : " + ArrayUtils.toString(sol));

    }

    @NotNull
    private static DenseDoubleMatrix1D getM(double[] x) {
        return new DenseDoubleMatrix1D(x);
    }


}





