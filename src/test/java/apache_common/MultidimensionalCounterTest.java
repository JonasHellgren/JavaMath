package apache_common;

import org.apache.commons.math3.util.MultidimensionalCounter;
import org.junit.Assert;
import org.junit.Test;

/**
 * This utility will convert from indices in a multidimensional structure
 *  * to the corresponding index in a one-dimensional array.
 */
public class MultidimensionalCounterTest {

    @Test
    public void whenTwoDimensions_thenCorrect() {

        MultidimensionalCounter mdc=new MultidimensionalCounter(3,2);
        System.out.println("mdc.getCount(0,0) = " + mdc.getCount(0, 0));
        System.out.println("mdc.getCount(2,0) = " + mdc.getCount(2, 0));
        System.out.println("mdc.getDimension() = " + mdc.getDimension());

        Assert.assertEquals(2,mdc.getDimension());

    }
}
