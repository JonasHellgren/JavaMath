package xchart;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.List;

public class BarChartOverlapped {

    public static void main(String[] args) {

        CategoryChart chart = new CategoryChartBuilder()
                .width(800).height(600).title("Score Histogram").xAxisTitle("Score").yAxisTitle("Number").build();
        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setOverlapped(true);

        // Series
        Histogram histogram1 = new Histogram(DataGenerator.getGaussianData(10000), 20, -20, 20);
        Histogram histogram2 = new Histogram(DataGenerator.getGaussianData(5000), 20, -20, 20);

        chart.addSeries("histogram 1", histogram1.getxAxisData(), histogram1.getyAxisData());
        chart.addSeries("histogram 2", histogram2.getxAxisData(), histogram2.getyAxisData());


        new SwingWrapper<>(chart).displayChart();
    }
}
