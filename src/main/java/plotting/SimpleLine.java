package plotting;

import org.knowm.xchart.*;

import java.io.IOException;

import static org.knowm.xchart.BitmapEncoder.*;
import static org.knowm.xchart.VectorGraphicsEncoder.saveVectorGraphic;


public class SimpleLine {

    public static final String CHART_DIR = "src/main/java/xchart/saved_charts/", FILE_NAME = "Sample_Chart";

    public static void main(String[] args) throws Exception {
        double[] xData = new double[]{0.0, 1.0, 2.0};
        double[] yData = new double[]{2.0, 1.0, 0.0};
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", null, yData);
        new SwingWrapper<>(chart).displayChart();
        saveChart(chart);
    }

    private static void saveChart(XYChart chart) throws IOException {
        saveBitmap(chart, CHART_DIR + FILE_NAME, BitmapFormat.PNG);
        saveBitmap(chart, CHART_DIR + FILE_NAME, BitmapFormat.JPG);
        saveJPGWithQuality(chart, CHART_DIR + "./Sample_Chart_With_Quality.jpg", 0.95f);
        saveBitmap(chart, CHART_DIR + FILE_NAME, BitmapFormat.BMP);
        saveBitmap(chart, CHART_DIR + FILE_NAME, BitmapFormat.GIF);

        saveBitmapWithDPI(chart, CHART_DIR + "/Sample_Chart_300_DPI", BitmapFormat.PNG, 300);
        saveBitmapWithDPI(chart, CHART_DIR + "/Sample_Chart_300_DPI", BitmapFormat.JPG, 300);
        saveBitmapWithDPI(chart, CHART_DIR + "/Sample_Chart_300_DPI", BitmapFormat.GIF, 300);

        saveVectorGraphic(chart, CHART_DIR + FILE_NAME, VectorGraphicsEncoder.VectorGraphicsFormat.EPS);
        saveVectorGraphic(chart, CHART_DIR + FILE_NAME, VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
        saveVectorGraphic(chart, CHART_DIR + FILE_NAME, VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
    }


}
