package guava;

import com.google.common.math.BigIntegerMath;

import java.math.BigInteger;
import java.math.RoundingMode;

public class GuavaBigInt {

    public static void main(String args[]) {
        GuavaBigInt tester = new GuavaBigInt();
        tester.demonstrateBigIntegerMathOperations();
    }

    private void demonstrateBigIntegerMathOperations() {
        testDivision();
        testLogarithms();
        testSquareRoot();
        testFactorial();
    }

    private void testDivision() {
        System.out.println("10 / 2 = " + BigIntegerMath.divide(BigInteger.TEN, BigInteger.valueOf(2L), RoundingMode.UNNECESSARY));
        try {
            // Exception will be thrown as 10 is not completely divisible by 3
            // thus rounding is required, and RoundingMode is set as UNNECESSARY
            System.out.println("10 / 3 = " + BigIntegerMath.divide(BigInteger.TEN, BigInteger.valueOf(3L), RoundingMode.UNNECESSARY));
        } catch (ArithmeticException e) {
            System.out.println("Error: Division of 10 by 3 without rounding is not possible. " + e.getMessage());
        }
    }

    private void testLogarithms() {
        System.out.println("Log2(2): " + BigIntegerMath.log2(BigInteger.valueOf(2L), RoundingMode.HALF_EVEN));
        System.out.println("Log10(10): " + BigIntegerMath.log10(BigInteger.TEN, RoundingMode.HALF_EVEN));
    }

    private void testSquareRoot() {
        System.out.println("sqrt(100): " + BigIntegerMath.sqrt(BigInteger.TEN.multiply(BigInteger.TEN), RoundingMode.HALF_EVEN));
    }

    private void testFactorial() {
        System.out.println("factorial(5): " + BigIntegerMath.factorial(5));
    }
}

