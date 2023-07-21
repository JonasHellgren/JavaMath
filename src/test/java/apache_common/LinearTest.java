package apache_common;

import org.apache.commons.math3.linear.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinearTest {

    @Test
    public void whenSolvingEqSystem_thenCorrect() {
        RealMatrix coefficients =
                new Array2DRowRealMatrix(new double[][] { { 2, 3, -2 }, { -1, 7, 6 }, { 4, -3, -5 } },
                        false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

        RealVector constants = new ArrayRealVector(new double[] { 1, -2, 1 }, false);
        RealVector solution = solver.solve(constants);
        System.out.println("solution = " + solution);

        RealVector answer = new ArrayRealVector(new double[] {-0.3698630137, 0.1780821918,-0.602739726}, false);
        RealVector diff = answer.subtract(solution);
        System.out.println("diff = " + diff);
        assertTrue(diff.getL1Norm()<0.001);
    }
}
