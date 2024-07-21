package plotting;

import plotting.table_shower.TableData;
import plotting.table_shower.TableSettings;
import plotting.table_shower.TableShower;
import javax.swing.*;

public class ShowTable {

    public static void main(String[] args) {
        int nX = 10;
        int nY = 5;
      //  var tableData= TableData.ofDouble(createDoubleTableData(nX, nY));
        var tableData= TableData.ofString(createStringTableData(nX, nY));
        var settings=TableSettings.defOfNxNy(nX,nY);
        var tableShower=new TableShower(settings);
        SwingUtilities.invokeLater(() -> {
            tableShower.showTable(tableData);
        });
    }

    static Double[][] createDoubleTableData(int nX, int nY) {
        Double[][] data0 = new Double[nX][nY];
        for (int yi = 0; yi < nY; yi++) {
            for (int xi = 0; xi < nX; xi++) {
                data0[xi][yi] = (double) (xi+yi); // Example function
            }
        }
        return data0;
    }

    static String[][] createStringTableData(int nX, int nY) {
        String[][] data0 = new String[nX][nY];
        for (int yi = 0; yi < nY; yi++) {
            for (int xi = 0; xi < nX; xi++) {
                data0[xi][yi] = "-"+(xi+yi)+"-"; // Example function
            }
        }
        return data0;
    }

}
