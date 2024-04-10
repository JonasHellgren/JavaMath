package plotting;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;


public class PieChart  {

    public static void main(String[] args) {
        var chart = new PieChartBuilder().width(800).height(600).title("Sample Pie Chart").build();
        chart.addSeries("Gold", 40);
        chart.addSeries("Silver", 30);
        chart.addSeries("Platinum", 20);
        chart.addSeries("Copper", 10);
        new SwingWrapper<>(chart).displayChart();
    }


}
