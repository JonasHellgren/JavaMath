package xchart;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;
import java.util.List;

public class BarChartsBeside {

    public static final List<String> CATEGORIES = List.of("cost buy", "cost degr", "income sell", "rev");

    public static void main(String[] args) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(500).height(300)
                .title("").xAxisTitle("Type").yAxisTitle("Euro")
                .theme(Styler.ChartTheme.GGPlot2).build();
        chart.addSeries("1",CATEGORIES, List.of(30,10,30,-10));
        chart.addSeries("2",CATEGORIES, List.of(30,10,50,10));
        new SwingWrapper<>(chart).displayChart();
    }

}
