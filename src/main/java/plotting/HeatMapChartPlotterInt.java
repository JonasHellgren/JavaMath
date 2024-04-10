package plotting;

import org.jetbrains.annotations.NotNull;
import org.knowm.xchart.AnnotationText;
import org.knowm.xchart.HeatMapChart;
import org.knowm.xchart.HeatMapChartBuilder;

import org.knowm.xchart.SwingWrapper;
import java.awt.*;
import java.util.stream.IntStream;

public class HeatMapChartPlotterInt {

    public static void main(String[] args) {
        int[][] data = getData();
        int[][] dataRot=transposeMatrix(data);
        HeatMapChart chart = createChart();
        addData(chart, dataRot);
        addCellText(chart, data);
        new SwingWrapper<>(chart).displayChart();
    }

    private static int[][] getData() {
        return new int[][]{
                {0, 1, 50, 100,10},  //(0,0), (1,0),...
                {2, 3, 5, 3,10},
                {2, 4, 3, 2,10},
                {30, 2, 4, 3,10}   //x(0,3), x(1,3),...
        };
    }

    @NotNull
    private static org.knowm.xchart.HeatMapChart createChart() {
        var chart = new HeatMapChartBuilder()
                .title("Sample HeatMap")
                .xAxisTitle("X Axis")
                .yAxisTitle("Y Axis")
                .width(300).height(300)
                .build();
        chart.getStyler().setChartTitleVisible(true).setLegendVisible(false);
        chart.getStyler().setMin(0).setMax(100).setRangeColors(new Color[]{Color.LIGHT_GRAY,Color.WHITE});
        chart.getStyler().setAnnotationTextFontColor(Color.BLACK);
        chart.getStyler().setAnnotationTextFont(new Font("Arial", Font.BOLD, 22));
        return chart;
    }

    private static void addData(HeatMapChart chart, int[][] data) {
        int[] xData = IntStream.rangeClosed(0, 4).toArray();
        int[] yData=IntStream.rangeClosed(0, 3).toArray();
        chart.addSeries("a", xData, yData, data);
    }

    private static void addCellText(org.knowm.xchart.HeatMapChart chart, int[][] data) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                String text = String.valueOf(data[y][x]);
                AnnotationText annotation = new AnnotationText(text, x,y, false);
                chart.addAnnotation(annotation); // Hypothetical method to add text
            }
        }

    }


    public static int[][] transposeMatrix(int[][] matrix) {
        // If the matrix is empty, return an empty matrix
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0][0];
        }

        int numRows = matrix.length; // Original number of rows
        int numCols = matrix[0].length; // Original number of columns
        int[][] transposedMatrix = new int[numCols][numRows];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                transposedMatrix[j][i] = matrix[i][j]; // Swap the row and column indices for the transposition
            }
        }

        return transposedMatrix;
    }


}
