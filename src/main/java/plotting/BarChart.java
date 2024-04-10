package plotting;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.List;

public class BarChart {

    public static void main(String[] args) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(800).height(600).title("Score Histogram").xAxisTitle("Score").yAxisTitle("Number").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.addSeries("test 1", List.of(0, 1, 2, 3, 4), List.of(4, 5, 9, 6, 5) );
        new SwingWrapper<>(chart).displayChart();
    }

}
