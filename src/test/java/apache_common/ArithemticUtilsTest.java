package apache_common;

import org.apache.commons.math3.util.ArithmeticUtils;
import org.junit.Assert;
import org.junit.Test;


public class ArithemticUtilsTest {

    @Test
    public void whenAddaAndCheck_thenCorrect() {
        Assert.assertEquals(2000, ArithmeticUtils.addAndCheck(1000,1000));
    }

    @Test
    public void whenMulAndCheck_thenCorrect() {
        Assert.assertEquals(1000_000, ArithmeticUtils.mulAndCheck(1000,1000));
    }

    @Test
    public void whenGcd_thenCorrect() {
        int gcd = ArithmeticUtils.gcd(1000, 100);  //greatest common divisor
        System.out.println("gcd = " + gcd);
        Assert.assertEquals(100, gcd);
    }

    @Test
    public void whenLcm_thenCorrect() {
        int lcm = ArithmeticUtils.lcm(4, 6);  //least common multiple of the absolute value of two numbers
        System.out.println("lcm = " + lcm);
        Assert.assertEquals(12, lcm);


    }


}
