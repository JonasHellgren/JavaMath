package xchart;

import org.knowm.xchart.HeatMapChartBuilder;
import org.knowm.xchart.SwingWrapper;
import java.awt.*;
import java.util.stream.IntStream;

public class HeatMapChart {

    public static void main(String[] args) {
        var chart = new HeatMapChartBuilder()
                .title("Sample HeatMap")
                .xAxisTitle("X Axis")
                .yAxisTitle("Y Axis")
                .build();

        chart.getStyler().setChartTitleVisible(true).setLegendVisible(false);
        chart.getStyler().setMin(0).setMax(100).setRangeColors(new Color[]{Color.BLACK,Color.WHITE});

         int[][] data = new int[][]{
                {0, 1, 50, 100},  //x(0,0), x(0,1),...
                {2, 3, 5, 3},
                {2, 4, 3, 2},
                {3, 2, 4, 3}   //x(3,0), x(3,1),...
        };
        int[] xData = IntStream.rangeClosed(0, 3).toArray();
        int[] yData=IntStream.rangeClosed(0, 3).toArray();
        chart.addSeries("a", xData, yData,data);
        new SwingWrapper<>(chart).displayChart();

    }


}
