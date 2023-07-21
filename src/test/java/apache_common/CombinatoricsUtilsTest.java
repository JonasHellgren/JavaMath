package apache_common;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CombinatoricsUtilsTest {

    @Test
    public void whenFactorial_thenCorrect() {
        assertEquals(6, CombinatoricsUtils.factorial(3));
    }

    /***
     * n over k
     * Example, 6 ways to choose 2 elements from {1,2,3,4}:  {1,2}, {1,3}  ..
     */

    @Test
    public void whenBinCoeff_thenCorrect() {
        assertEquals(6, CombinatoricsUtils.binomialCoefficient(4,2));
    }

}
