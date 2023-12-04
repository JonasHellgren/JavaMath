package xchart;

import org.apache.commons.math3.util.Pair;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.knowm.xchart.BitmapEncoder.*;

public class TradingChartsExample {
    public static final String CHART_DIR = "src/main/java/xchart/saved_charts_trading/";
    public static final int WIDTH = 400, HEIGHT = 200;
    public static final String X_TITLE = "Time (h)";
    public static final double[] X_DATA = {0.0, 4.0, 8.0, 12.0, 16.0, 20.0, 24.0};

    public static void main(String[] args) throws Exception {

        List<XYChart> inputCharts = new ArrayList<>();
        XYChart chartElp = getChartWithYLabelAndMinMaxY("El.Price (SEK/kWh)",Pair.create(0d,5d));
        addSeries(chartElp, X_DATA, new double[]{1.0, 1.1, 1.2, 3.0,  4.0, 5.0, 1.0}, "Day ahead market");
        inputCharts.add(chartElp);
        XYChart chartKWPrice = getChartWithYLabelAndMinMaxY("Power compensation (SEK/MW)",Pair.create(0d,60d));
        chartKWPrice.getStyler().setYAxisMax(60d);
        addSeries(chartKWPrice, X_DATA, new double[]{30, 31, 32, 30,  40, 50, 30}, "Freq regulation market");
        inputCharts.add(chartKWPrice);

        new SwingWrapper<>(inputCharts).displayChartMatrix();

        List<XYChart> socTrajCharts = new ArrayList<>();
        XYChart chartSoCDaAheadOnly = getChartWithYLabelAndMinMaxY("SoC (%)",Pair.create(0d,100d));
        addSeries(chartSoCDaAheadOnly, X_DATA, new double[]{50, 90, 90, 90,  90, 20, 50}, "Day ahead market");
        socTrajCharts.add(chartSoCDaAheadOnly);
        XYChart chartSoCFreq = getChartWithYLabelAndMinMaxY("SoC (%)",Pair.create(0d,10d));
        chartSoCFreq.getStyler().setYAxisMin(0d).setYAxisMin(100d);
        addSeries(chartSoCFreq, X_DATA, new double[]{50, 70, 70, 70, 40, 40, 50}, "Freq regulation market");
        socTrajCharts.add(chartSoCFreq);

        new SwingWrapper<>(socTrajCharts).displayChartMatrix();

    }

    private static void addSeries(XYChart chartElp, double[] time, double[] elPrice, String seriesName) {
        XYSeries series = chartElp.addSeries(seriesName, time, elPrice);
        series.setMarker(SeriesMarkers.NONE);
    }

    private static XYChart getChartWithYLabelAndMinMaxY(String yAxisTitle, Pair<Double,Double> minMaxY) {
        XYChart chart = new XYChartBuilder()
                .xAxisTitle(X_TITLE).yAxisTitle(yAxisTitle).width(WIDTH).height(HEIGHT).build();
        chart.getStyler().setYAxisMin(minMaxY.getFirst()).setYAxisMax(minMaxY.getSecond());
        return chart;
    }

    private static void saveChart(XYChart chart, String fileName) throws IOException {
        saveBitmap(chart, CHART_DIR + fileName, BitmapEncoder.BitmapFormat.PNG);
    }

}
