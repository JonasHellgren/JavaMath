package apache_common;

import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FractionTest {

    @Test
    public void whenFraction1_4_thenCorrect() {
        Fraction frac1Div4=Fraction.getReducedFraction(1,4);
        System.out.println("frac1Div4 = " + frac1Div4);
    }


    @Test
    public void whenSummingFraction1_4_thenCorrect() {
        Fraction frac1Div4=Fraction.getReducedFraction(1,4);
        assertEquals(Fraction.getReducedFraction(1,2),frac1Div4.add(frac1Div4));
    }

}
