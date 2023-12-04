package xchart;

import org.apache.commons.math3.util.Pair;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;
import java.util.ArrayList;
import java.util.List;

public class TradingChartsExample {
    public static final int WIDTH = 400, HEIGHT = 200;
    public static final String X_TITLE = "Time (h)";
    public static final double[] X_DATA = {0.0, 4.0, 8.0, 12.0, 16.0, 20.0, 24.0};

    public static void main(String[] args) throws Exception {

        List<XYChart> inputCharts = new ArrayList<>();
        addElPriceChart(inputCharts);
        addPowerReservePriceChart(inputCharts);
        new SwingWrapper<>(inputCharts).displayChartMatrix();

        List<XYChart> socTrajCharts = new ArrayList<>();
        addOnlyDayAhead(socTrajCharts);
        addOnlyFreq(socTrajCharts);
        addBoth1(socTrajCharts);
        addBoth2(socTrajCharts);
        new SwingWrapper<>(socTrajCharts).displayChartMatrix();

    }

    private static void addBoth2(List<XYChart> socTrajCharts) {
        XYChart chartSoCBoth2 = getChartWithYLabelAndMinMaxY("SoC (%)",Pair.create(0d,100d));
        addSeries(chartSoCBoth2, X_DATA, new double[]{50, 80, 80, 80, 30, 30, 50}, "Both markets 2");
        socTrajCharts.add(chartSoCBoth2);
    }

    private static void addBoth1(List<XYChart> socTrajCharts) {
        XYChart chartSoCBoth1 = getChartWithYLabelAndMinMaxY("SoC (%)",Pair.create(0d,100d));
        addSeries(chartSoCBoth1, X_DATA, new double[]{50, 70, 70, 70, 40, 40, 50}, "Both markets 1");
        socTrajCharts.add(chartSoCBoth1);
    }

    private static void addOnlyFreq(List<XYChart> socTrajCharts) {
        XYChart chartSoCFreq = getChartWithYLabelAndMinMaxY("SoC (%)",Pair.create(0d,100d));
        addSeries(chartSoCFreq, X_DATA, new double[]{50, 50, 50, 50, 50, 50, 50}, "Freq regulation market");
        socTrajCharts.add(chartSoCFreq);
    }

    private static void addOnlyDayAhead(List<XYChart> socTrajCharts) {
        XYChart chartSoCDaAheadOnly = getChartWithYLabelAndMinMaxY("SoC (%)",Pair.create(0d,100d));
        addSeries(chartSoCDaAheadOnly, X_DATA, new double[]{50, 90, 90, 90,  90, 20, 50}, "Day ahead market");
        socTrajCharts.add(chartSoCDaAheadOnly);
    }

    private static void addPowerReservePriceChart(List<XYChart> inputCharts) {
        XYChart chartKWPrice = getChartWithYLabelAndMinMaxY("Power reserve (SEK/MW)",Pair.create(0d,60d));
        chartKWPrice.getStyler().setYAxisMax(60d);
        var errorBar=new double[]{10, 11, 22, 20,  10, 20, 20};
        XYSeries series = chartKWPrice.addSeries("Freq regulation market"
                , X_DATA, new double[]{30, 31, 32, 30,  40, 50, 30},errorBar);
        series.setMarker(SeriesMarkers.NONE);
        inputCharts.add(chartKWPrice);
    }

    private static void addElPriceChart(List<XYChart> inputCharts) {
        XYChart chartElp = getChartWithYLabelAndMinMaxY("El.Price (SEK/kWh)",Pair.create(0d,5d));
        addSeries(chartElp, X_DATA, new double[]{1.0, 1.1, 1.2, 3.0,  4.0, 5.0, 1.0}, "Day ahead market");
        inputCharts.add(chartElp);
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


}
