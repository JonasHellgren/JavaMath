package optimization_apache;

import optimization_apache.models.OneDimLine;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.junit.Test;

import java.util.Arrays;

public class TestOneDimLine {

    public static final double DELTA = 1.0e-1;
    public static final int NOF_EVAL_MAX = 1000;
    public static final double RELATIVE_THRESHOLD = 1e-1;
    public static final double ABSOLUTE_THRESHOLD = 1e-2;
    double[] initialGuess = {1.0};

    @Test
    public void givenLine_thenZeroIsOptimum() {
        OneDimLine line=new OneDimLine();
        MultivariateOptimizer optimizer =
                TestHelper.getConjugateGradientOptimizer(RELATIVE_THRESHOLD,ABSOLUTE_THRESHOLD);
        PointValuePair optimum = TestHelper.gradientOptimize(
                optimizer,line.getObjectiveFunction(),line.getGradient(),
                initialGuess, NOF_EVAL_MAX);

        printAndAssert(optimum);
    }

    private static void printAndAssert(PointValuePair optimum) {
        System.out.println("optimum.getPointRef() = " + Arrays.toString(optimum.getPointRef()));
        double x=optimum.getPointRef()[0];
        System.out.println("x = " + x);
    }
}
