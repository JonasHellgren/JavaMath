package apache_common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.MultidimensionalCounter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NumberUtilsTest {

    public static final double DELTA_DOUBLE = 0.01;

    @Test
    public void whenNotDigits_thenCorrect() {
        assertFalse(NumberUtils.isDigits("23g"));
        assertFalse(NumberUtils.isDigits("23.22"));
    }

    @Test
    public void whenDigits_thenCorrect() {
        assertTrue(NumberUtils.isDigits("22"));
    }


    @Test
    public void whenCompare_thenCorrect() {
        assertEquals(0,NumberUtils.compare(22,22));
        assertEquals(-1,NumberUtils.compare(21,22));
        assertEquals(1,NumberUtils.compare(23,22));
     //   assertEquals(1,NumberUtils.compare(23.22,22.3));  //not double
    }


    @Test
    public void whenMax_thenCorrect() {
        List<Double> doubleList = Arrays.asList(10d, 6d, 12d, 90d, 34d);
        Double[] array = doubleList.toArray(new Double[0]);
        double[] doubleArray = ArrayUtils.toPrimitive(array);
        assertEquals(90,NumberUtils.max(doubleArray), DELTA_DOUBLE);
    }


    @Test
    public void whenToDouble_thenCorrect() {
        assertEquals(22d,NumberUtils.toDouble("22"),DELTA_DOUBLE);
    }

    @Test
    public void whenToDoubleDefault_thenCorrect() {
        assertEquals(22d,NumberUtils.toDouble("22d",22),DELTA_DOUBLE);


    }

}
