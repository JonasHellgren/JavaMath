package plotting;

import common.RandUtils;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.util.LinkedList;
import java.util.List;

public class ScatterChart {

    public static void main(String[] args) {

        XYChart chart = getChart();
        new SwingWrapper<>(chart).displayChart();
    }

    public static XYChart getChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(400).height(300).build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(5);

        // Series
        List<Double> xData = new LinkedList<>();
        List<Double> yData = new LinkedList<>();
        int size = 100;
        for (int i = 0; i < size; i++) {
            xData.add(RandUtils.getRandomDouble(-10,10));
            yData.add(RandUtils.getRandomDouble(-10,10));

        }
        chart.addSeries("Scatter", xData, yData);

        return chart;
    }


}
