package plotting;

import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

public class MultiPlotsOneRow {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;

    public static void main(String[] args) {
        // Create several XYSeries
        XYSeries series1 = new XYSeries("Series 1");
        series1.add(1, 1);
        series1.add(2, 4);
        series1.add(3, 3);

        XYSeries series2 = new XYSeries("Series 2");
        series2.add(1, 2);
        series2.add(2, 5);
        series2.add(3, 6);

        XYSeries series3 = new XYSeries("Series 3");
        series3.add(1, 5);
        series3.add(2, 3);
        series3.add(3, 7);

        // Create datasets and charts for each series
        JFrame frame = new JFrame("Multiple Line Charts");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Process each series
        XYSeries[] allSeries = {series1, series2, series3};
        for (XYSeries series : allSeries) {
            JFreeChart chart = getChart(series);
            chart.removeLegend();
            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            frame.add(panel);
        }

        // Display the JFrame
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    @NotNull
    private static JFreeChart getChart(XYSeries series) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return ChartFactory.createXYLineChart(
                series.getKey().toString(),  // Chart title
                "X",                         // X-Axis label
                "Y",                         // Y-Axis label
                dataset,                     // Dataset
                PlotOrientation.VERTICAL,
                true,                        // Show legend
                true,                        // Use tooltips
                false                        // Configure chart to generate URLs?
        );
    }

}
