package xchart;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorBarChart {

    public static void main(String[] args) {

        XYChart chart = getChart();
        new SwingWrapper<>(chart).displayChart();
    }


    public static XYChart getChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title("ScatterChart04").xAxisTitle("X").yAxisTitle("Y").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setXAxisDecimalPattern("0.00");

        // Series
        int size = 10;
        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        List<Double> errorBars = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            xData.add(((double) i) / 10);
            yData.add(10 * Math.exp(-i));
            errorBars.add(Math.random() + .3);
        }
        XYSeries series = chart.addSeries("10^(-x)", xData, yData, errorBars);
        series.setMarkerColor(Color.GRAY);
        series.setMarker(SeriesMarkers.SQUARE);

        return chart;
    }

}
