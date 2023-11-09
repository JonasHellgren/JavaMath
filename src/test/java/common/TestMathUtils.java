package common;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class TestMathUtils {


    @Test
    public void whenAccumulatedSum_thenCorrect() {
        List<Double> accumulatedSum=MathUtils.accumulatedSum(List.of(1d,2d,3d));
        Assertions.assertEquals(List.of(1d,3d,6d),accumulatedSum);
    }

}
