package xchart;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.util.List;

public class LinesInSubPlots {

    public static void main(String[] args) {
        int numCharts = 4;
        List<XYChart> charts = new ArrayList<>();

        for (int i = 0; i < numCharts; i++) {
            XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
            chart.getStyler().setYAxisMin(-10d);
            chart.getStyler().setYAxisMax(10d);
            XYSeries series = chart.addSeries("" + i, null, DataGenerator.getRandomWalk(200));
            series.setMarker(SeriesMarkers.NONE);
            charts.add(chart);
        }
        new SwingWrapper<>(charts).displayChartMatrix();
    }

}
