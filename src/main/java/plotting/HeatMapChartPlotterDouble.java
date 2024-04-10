package plotting;


    public class HeatMapChartPlotterDouble {

        public static void main(String[] args) {
            double[][] data = {
                    {1.0, 2.0, 3.0},
                    {2.0, 3.0, 4.0},
                    {5.0, 4.0, 5.0}
            };


            var shower=SwingShowHeatMap.builder()
                 //   .xLabels(new String[]{"X1", "X2", "X3"})
              //      .yLabels(new String[]{"Y1", "Y2", "Y3"})
                    .isLabels(false)
                    .build();

            shower.showMap(data,"map");
        }



    }




