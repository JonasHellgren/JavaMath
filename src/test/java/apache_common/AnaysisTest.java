package apache_common;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.analysis.solvers.AllowedSolution;
import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnaysisTest {

    public static final double DELTA = 0.01;

    @Test
    public void whenInterpolation_thenCorrect() {

        double[] xArr = { 0.0, 1.0, 2.0 };
        double[] yArr = { 0.0, 1.0, 2.0};
        UnivariateInterpolator interpolator = new LinearInterpolator();
        UnivariateFunction function = interpolator.interpolate(xArr, yArr);
        double interpolationX = 0.5;
        double interpolatedY = function.value(interpolationX);
        System.out.println("f(" + interpolationX + ") = " + interpolatedY);
        assertEquals(0.5,interpolatedY, DELTA);
    }

    @Test
    public void whenSolver_thenCorrect() {
        UnivariateFunction function = (x) -> x;
        final double relativeAccuracy = 1.0e-12;
        final double absoluteAccuracy = 1.0e-8;
        final int    maxOrder         = 5;
        UnivariateSolver solver   = new BracketingNthOrderBrentSolver(relativeAccuracy, absoluteAccuracy, maxOrder);
        double c = solver.solve(100, function, -1.0, 5.0);
        System.out.println("c = " + c);

        assertEquals(0,c,DELTA);

    }
}
