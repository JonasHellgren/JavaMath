package common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIndexFinder {

    int[] arrayInt;
    double[] arrayDouble;


    @BeforeEach
    public void init() {
        arrayInt = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        arrayDouble = new double[]{0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0};

    }

    @Test
    public void givenIntNumber_thenCorrectIndex() {
        Assertions.assertEquals(0,IndexFinder.findIndex(arrayInt,0));
        Assertions.assertEquals(4, IndexFinder.findIndex(arrayInt, 4));
        Assertions.assertEquals(7, IndexFinder.findIndex(arrayInt, 7));
        Assertions.assertEquals(-1, IndexFinder.findIndex(arrayInt, -7));
    }

    @Test
    public void givenDoubleNumber_thenCorrectIndex() {
        Assertions.assertEquals(0,IndexFinder.findBucket(arrayDouble,0.5));
        Assertions.assertEquals(4, IndexFinder.findBucket(arrayDouble, 4.2));
        Assertions.assertEquals(8, IndexFinder.findBucket(arrayDouble, 42));
        Assertions.assertEquals(-1, IndexFinder.findBucket(arrayDouble, -7));
    }

}
