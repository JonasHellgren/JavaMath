package plotting;


import plotting.domain.SwingShowHeatMap;

public class HeatMapChartPlotterString {

        public static void main(String[] args) {
            String[][] data = {
                    {"a", "a", "a"},
                    {"b", "b", "b"},
                    {"c", "c", "c"}
            };


            var shower= SwingShowHeatMap.builder()
                    .xLabels(new String[]{"X1", "X2", "X3"})
                    .yLabels(new String[]{"Y1", "Y2", "Y3"})
                    .isLabels(true)
                    .build();

            shower.showMap(data,"map");
        }



    }




