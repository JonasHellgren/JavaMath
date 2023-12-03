package xchart;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.List;

public class BarChartsBeside {

    public static void main(String[] args) {

        CategoryChart chart = getChart();
        new SwingWrapper<>(chart).displayChart();
    }

    public static CategoryChart getChart() {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(500).height(300)
                .title("").xAxisTitle("Type").yAxisTitle("Euro")
                .theme(Styler.ChartTheme.GGPlot2).build();
//        chart.addSeries("fish",List.of("Blue", "Red"), List.of(-40, 30));
  //      chart.addSeries("worms", List.of("Blue", "Red"), List.of(0, 10));

        chart.addSeries("1",List.of("cost buy","cost degr","income sell","rev"), List.of(30,10,30,-10));
        chart.addSeries("2",List.of("cost buy","cost degr","income sell","rev"), List.of(30,10,50,10));


        return chart;
    }


}
