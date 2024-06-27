package plotting.domain;

import com.google.common.collect.Lists;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.List;

public class StairDataGenerator {

    private StairDataGenerator() {
    }

    public static Pair<List<Double>, List<Double>> generateWithEndStep(Pair<double[], double[]> xyData) {
        var xyStairData = generate(xyData);
        addEndStep(xyStairData.getFirst(), xyStairData.getSecond(), xyData);
        return Pair.create(xyStairData.getFirst(), xyStairData.getSecond());
    }

    public static Pair<List<Double>, List<Double>> generate(Pair<double[], double[]> xyData) {
        List<Double> xStairData = Lists.newArrayList();
        List<Double> yStairData = Lists.newArrayList();
        addFirstSteps(xStairData, yStairData, xyData);
        return Pair.create(xStairData, yStairData);
    }

    static void addFirstSteps(List<Double> xStairData,
                              List<Double> yStairData,
                              Pair<double[], double[]> xyData) {
        var xData = xyData.getFirst();
        var yData = xyData.getSecond();
        int len = xData.length;
        for (int i = 0; i < len - 1; i++) {
            xStairData.add(xData[i]);
            yStairData.add(yData[i]);
            xStairData.add(xData[i + 1]);
            yStairData.add(yData[i]);
        }
    }

    static void addEndStep(List<Double> xStairData,
                           List<Double> yStairData,
                           Pair<double[], double[]> xyData) {
        var xData = xyData.getFirst();
        var yData = xyData.getSecond();
        int len = xData.length;
        double max = Arrays.stream(xData).max().orElseThrow();
        double min = Arrays.stream(xData).min().orElseThrow();
        double step = (max - min) / len;
        xStairData.add(xData[len - 1]);
        yStairData.add(yData[yData.length - 1]);
        xStairData.add(xData[len - 1] + step);
        yStairData.add(yData[yData.length - 1]);
    }

}
