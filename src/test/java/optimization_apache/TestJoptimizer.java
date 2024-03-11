package optimization_apache;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.jet.math.Mult;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.NewtonLEConstrainedFSP;
import com.joptimizer.optimizers.OptimizationRequest;
import com.joptimizer.optimizers.OptimizationResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import optimization_apache.depot_charging.DepotModel;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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

    MyModel model2;


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

        model2=new MyModel();

    }

    public class MyModel {

        // Method to calculate the value of the objective function
        public double calculateObjectiveValue(double[] x) {
            double x1 = x[0];
            double x2 = x[1];
            return Math.pow(x1 - 1, 2) + Math.pow(x2 - 2, 2);
        }

        // Method to calculate the gradient of the objective function
        public double[] calculateObjectiveGradient(double[] x) {
            double x1 = x[0];
            double x2 = x[1];
            double gradX1 = 2 * (x1 - 1);
            double gradX2 = 2 * (x2 - 2);
            return new double[]{gradX1, gradX2};
        }
    }

    ConvexMultivariateRealFunction objectiveFunction = new ConvexMultivariateRealFunction() {
        @Override
        public double value(DoubleMatrix1D dm) {
            return model2.calculateObjectiveValue(dm.toArray());
            //return model.getObjectiveFunction().getObjectiveFunction().value(dm.toArray());
        }

        @Override
        public DoubleMatrix1D gradient(DoubleMatrix1D dm) {
            return getM(model2.calculateObjectiveGradient(dm.toArray()));
            //return null;
            //return  new DenseDoubleMatrix1D(
              //      model.getFiniteDiffGradient().getObjectiveFunctionGradient().value(dm.toArray()));
        }

        @Override
        public DoubleMatrix2D hessian(DoubleMatrix1D doubleMatrix1D) {
            DoubleFactory2D F2 = DoubleFactory2D.dense;
            return F2.make(new double[][] {
                    { 1.0, 0.0},
                    { 0.0, 1 }});
        }

        @Override
        public int getDim() {
            return 2;
        }


    };


    @SneakyThrows
    @Test
    public void when() {
/*
        DoubleFactory1D F1 = DoubleFactory1D.dense;
        DoubleFactory2D F2 = DoubleFactory2D.dense;
        DoubleMatrix2D pMatrix = F2.make(new double[][] {
                { 1.68, 0.34, 0.38 },
                { 0.34, 3.09, -1.59 },
                { 0.38, -1.59, 1.54 } });
        DoubleMatrix1D qVector = F1.make(new double[] { 0.018, 0.025, 0.01 });
        double theta = 0.01522;
        DoubleMatrix2D P = pMatrix.assign(Mult.mult(theta));
        DoubleMatrix1D q = qVector.assign(Mult.mult(-1));
      //  var objectiveFunction = new PDQuadraticMultivariateRealFunction(P.toArray(), q.toArray(), 0);

        System.out.println("objectiveFunction = " + objectiveFunction.value(getM(INITIAL_GUESS)));
        System.out.println("objectiveFunction.gradient(getM(INITIAL_GUESS)).toArray() = " + Arrays.toString(objectiveFunction.gradient(getM(INITIAL_GUESS)).toArray()));
        System.out.println("objectiveFunction.hessian(getM(INITIAL_GUESS)) = " + objectiveFunction.hessian(getM(INITIAL_GUESS)));

*/

        OptimizationRequest or = new OptimizationRequest();
        or.setF0(objectiveFunction); // Set the objective function
        or.setInitialPoint(new double[]{0.1, 0.1}); // Optional: initial guess
        or.setToleranceFeas(1.E-9); // Tolerance on feasibility
        or.setTolerance(1.E-9); // Tolerance on optimization

        NewtonLEConstrainedFSP  optimizer = new NewtonLEConstrainedFSP();
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





