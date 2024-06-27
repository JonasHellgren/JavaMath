package plotting;

import org.apache.commons.math3.util.Pair;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;
import plotting.domain.StairDataGenerator;

public class StairsChart {
    public static void main(String[] args) {
        // Example data points
        double[] xData = {0, 1, 2};
        double[] yData = {0,1,0};

        var xyDataStair= StairDataGenerator.generateWithEndStep(Pair.create(xData,yData));
        System.out.println("xyDataStair = " + xyDataStair);

        XYChart chart = new XYChartBuilder().width(800).height(600).title("Stairs Plot").xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setLegendVisible(false);
        chart.addSeries("Stairs", xyDataStair.getFirst(), xyDataStair.getSecond()).setMarker(SeriesMarkers.NONE);
        new SwingWrapper<>(chart).displayChart();
    }
}
