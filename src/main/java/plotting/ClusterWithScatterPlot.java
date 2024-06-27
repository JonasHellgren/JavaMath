package plotting;

import apache_common.clusterer.KMeansClusterer;
import apache_common.clusterer.PointInCluster;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ClusterWithScatterPlot {

    public static final int MAX_X = 20;
    public static final int MAX_Y = 10;
    public static final int N_POINTS = 50;
    public static final int N_DIM = 2;
    public static final int N_CLUSTERS = 4;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 200;
    public static final int INDEX_X = 0;
    public static final int INDEX_Y = 1;

    public static void main(String[] args) {
        var clusterer = KMeansClusterer.of(N_DIM, N_CLUSTERS);
        addPoints(clusterer);
        var clusters = clusterer.getClusters();
        var chart = createChart();
        fillChartWithData(clusters, chart);
        new SwingWrapper<>(chart).displayChart();
    }

    private static void fillChartWithData(List<CentroidCluster<PointInCluster>> clusters, XYChart chart) {
        Random random = new Random();
        for (int i = INDEX_X; i < clusters.size(); i++) {
            var xData = getDataFromCluster(clusters.get(i), INDEX_X);
            var yData = getDataFromCluster(clusters.get(i), INDEX_Y);
            var series = chart.addSeries("cluster" + i, xData, yData);
            series.setMarkerColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
        }
    }

    static List<Double> getDataFromCluster(CentroidCluster<PointInCluster> cluster, int index) {
        return cluster.getPoints().stream()
                .map(point -> point.getPoint()[index])
                .toList();
    }

    static void addPoints(KMeansClusterer clusterer) {
        IntStream.range(0, N_POINTS)
                .mapToObj(i -> new double[]{
                        RandomUtils.nextDouble(0, MAX_X),
                        RandomUtils.nextDouble(0, MAX_Y)})
                .forEach(clusterer::addPoint);
    }

    static XYChart createChart() {
        XYChart chart = new XYChartBuilder()
                .width(WIDTH).height(HEIGHT)
                .title("Scatter Plot with Different Colors").xAxisTitle("X").yAxisTitle("Y")
                .build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setMarkerSize(5);
        return chart;
    }
}
