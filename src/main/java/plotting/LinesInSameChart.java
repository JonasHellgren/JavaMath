package plotting;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class LinesInSameChart {

    public static void main(String[] args) {
        int nofLines = 4;
        XYChart chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
        XYStyler styler = chart.getStyler();
        styler.setYAxisMin(-10d);
        styler.setYAxisMax(10d);
        styler.setPlotGridLinesVisible(true);

        for (int i = 0; i < nofLines; i++) {
            XYSeries series = chart.addSeries("" + i, null, DataGenerator.getRandomWalk(200));
            series.setMarker(SeriesMarkers.NONE);
        }
        var sw= new SwingWrapper<>(chart).displayChart();
    }


}
