package optimization_apache;

import apache_common.OptimizeAdapter;
import common.CpuTimer;
import optimization_apache.models.SumOfThreeModel;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.junit.Assert;
import org.junit.Test;

public class TestSumOfThreeUsingAdapter {

    public static final double DELTA = 1.0e-1;
    public static final int NOF_EVAL_MAX = 10_000;
    public static final double EPS = 1e-3;
    public static final double PEN_COEFF = 1e1;
    public static final double RELATIVE_THRESHOLD = EPS;
    public static final double ABSOLUTE_THRESHOLD = EPS;
    public static final double[] OPT_POINT = {0, 0, 1.0};

    double[] initialGuess = {0.5, 0.5, 0.5};


    @Test
    public void givenAdapter_thenCorrectOptimum() {
        MultivariateOptimizer optimizer =
                TestHelper.getConjugateGradientOptimizer(RELATIVE_THRESHOLD, ABSOLUTE_THRESHOLD);
        SumOfThreeModel sumOfThree = new SumOfThreeModel(PEN_COEFF, EPS);

        OptimizeAdapter adapter = new OptimizeAdapter(sumOfThree);
        PointValuePair optimum = TestHelper.gradientOptimize(
                optimizer, adapter.getObjectiveFunction(), adapter.getGradient(),
                initialGuess, NOF_EVAL_MAX);

        TestHelper.printPointValuePair(optimum);

        TestHelper.printOptimizerStats(optimizer);
        Assert.assertArrayEquals(OPT_POINT, optimum.getPointRef(), DELTA);
    }
}
