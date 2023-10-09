package apache_common;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * https://commons.apache.org/proper/commons-math/userguide/stat.html
 */

public class StatisticsTest {


    public static final double DELTA = 0.01;
    public static final List<Double> VALUES = List.of(1d, 2d, 3d, 4d);

    @Test
    public void whenRegression_thenCorrect() {
        double[][] data = { { 1, 3 }, {2, 5 }, {3, 7 }, {4, 14 }, {5, 11 }};
        SimpleRegression regression = new SimpleRegression();
        regression.addData(data);
        System.out.println("regression.getSlope() = " + regression.getSlope());
        assertEquals(2.5,regression.getSlope(), DELTA);
    }

    /**
     * DescriptiveStatistics maintains the input data in memory and has the capability of producing "rolling" statistics computed from a "window" consisting of the most recently added values.
     *
     * SummaryStatistics does not store the input data values in memory, so the statistics included in this aggregate are limited to those that can be computed in one pass through the data without access to the full array of values.
     */

    @Test
    public void whenDescriptiveStatistics_thenCorrect() {
        DescriptiveStatistics ds=new DescriptiveStatistics();
        addValues(ds, VALUES);

        System.out.println("ds.getMean() = " + ds.getMean());
        System.out.println("ds.getStandardDeviation() = " + ds.getStandardDeviation());
        System.out.println("p10 = " + ds.getPercentile(10));
        System.out.println("p90 = " + ds.getPercentile(90));

        assertEquals(2.5,ds.getMean(),DELTA);


    }

    private static void addValues(DescriptiveStatistics ds, List<Double> values) {
        values.forEach(ds::addValue);
    }

    @Test
    public void whenDescriptiveStatisticsWindow2_thenCorrect() {
        DescriptiveStatistics ds=new DescriptiveStatistics();
        ds.setWindowSize(2);
        addValues(ds, VALUES);

        System.out.println("ds.getMean() = " + ds.getMean());
        System.out.println("ds.getStandardDeviation() = " + ds.getStandardDeviation());
        System.out.println("ds.getPercentile(0.9) = " + ds.getPercentile(0.9));

        assertEquals(3.5,ds.getMean(),DELTA);

    }

}
