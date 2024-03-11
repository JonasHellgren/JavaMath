package optimization_apache;

import optimization_apache.depot_charging.DepotModel;
import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;
import org.apache.commons.math3.optim.nonlinear.scalar.gradient.NonLinearConjugateGradientOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestDepotApacheOptimizers {

    public static final double REL_TRES_HOLD = Double.MIN_VALUE;
    public static final double ABS_TRES_HOLD = 1e-10;

    public static final double BARRIER_WEIGHT = 1e2;
    public static final double TOL = 1e-4;
    public static final int EVAL_MAX = 100_000;
    public static final double[] INITIAL_GUESS = {0, 0, 0};
    public static final int MAX_ITERATIONS = 100_000;
    public static final double COST_TOL = 0.3;
    public static final double EXPECTED_COST = -0.5 * 2 - 0.5 * 3;
    DepotModel model;
    CMAESOptimizer optimizerCm;
    MultivariateOptimizer optimizerMv;
    SimplexOptimizer optimizerSm;

    @Before
    public void init() {
        model= DepotModel.builder()
                .kList(new double[]{1,2,3})
                .pMaxList(new double[]{0.5,0.5,0.5})
                .socList(new double[]{1,1,1})
                .pDepotMax(1)
                .barrierWeight(BARRIER_WEIGHT)
                .barrierWeightQuad(BARRIER_WEIGHT)
                .socMax(1)
                .build();

        optimizerMv=new NonLinearConjugateGradientOptimizer(
                NonLinearConjugateGradientOptimizer.Formula.POLAK_RIBIERE,
                new SimpleValueChecker(REL_TRES_HOLD,ABS_TRES_HOLD));

        optimizerCm=new CMAESOptimizer(
                MAX_ITERATIONS, // Max iterations
                1e-6, // Stop fitness (tolerance for considering a solution good enough)
                true, // Whether to generate diagnostics
                1, // Check period
                1, // Random seed for reproducibility, use any integer for deterministic results
                new JDKRandomGenerator(),
                false,
                new SimpleValueChecker(REL_TRES_HOLD,ABS_TRES_HOLD)
        );

        optimizerSm=new SimplexOptimizer(REL_TRES_HOLD,ABS_TRES_HOLD);

    }

    @Test
    public void whenMultivariate_thenWeightDepResult() {
        model.setBarrierWeightLin(0);
        var pvPair0=gradientOptimize(optimizerMv,model.getObjectiveFunction(),
                model.getFiniteDiffGradient(), INITIAL_GUESS, EVAL_MAX);
        printRes(pvPair0);

        model.setBarrierWeightLin(BARRIER_WEIGHT);
        var pvPair1=gradientOptimize(optimizerMv,model.getObjectiveFunction(),
                model.getFiniteDiffGradient(), INITIAL_GUESS, EVAL_MAX);
        printRes(pvPair1);

        Assert.assertNotEquals(pvPair0.getPoint(),pvPair1.getPoint());

        Assert.assertEquals(EXPECTED_COST,pvPair1.getValue(), COST_TOL);
    }

    @Test
    public void whenCmaes_thenCorrect() {

        var pvPair=optimizerCm.optimize(new MaxEval(EVAL_MAX),
                model.getObjectiveFunction(),
                GoalType.MINIMIZE,
                new InitialGuess(INITIAL_GUESS),
                new SimpleBounds(new double[]{0.0,0.0,0.0}, new double[]{1.0,1.0,1.0}),
                new CMAESOptimizer.Sigma(new double[]{1.0,1.0,1.0}),
                new CMAESOptimizer.PopulationSize(50000) );
        printRes(pvPair);

        Assert.assertEquals(EXPECTED_COST,pvPair.getValue(), COST_TOL);

    }

    @Test
    public void whenSimplex_thenCorrect() {
        var simplex = new NelderMeadSimplex(new double[]{1.0,1.0,1.0});
        var pvPair=optimizerSm.optimize(new MaxEval(EVAL_MAX),
                model.getObjectiveFunction(),
                GoalType.MINIMIZE,
                new InitialGuess(INITIAL_GUESS),
                simplex);
        printRes(pvPair);

        Assert.assertEquals(EXPECTED_COST,pvPair.getValue(), COST_TOL);

    }

    private void printRes(PointValuePair pvPair) {
        System.out.println("pvPair.getPoint() = " + Arrays.toString(pvPair.getPoint()));
        System.out.println("cost = " + pvPair.getValue());

        System.out.println("model.violations(pvPair.getFirst()) = " + model.violations(pvPair.getFirst()));
    }


    static PointValuePair gradientOptimize(MultivariateOptimizer optimizer,
                                           ObjectiveFunction objFunction,
                                           ObjectiveFunctionGradient gradientFunction,
                                           double[] initialGuess, int nofEvalMax) {
        PointValuePair optimize = optimizer.optimize(new MaxEval(nofEvalMax),
                objFunction, gradientFunction,
                GoalType.MINIMIZE,
                new InitialGuess(initialGuess));
        System.out.println("iter = " + optimizer.getIterations());
        return optimize;
    }


}
