package plotting;

import plotting.table_shower.*;
import javax.swing.*;
import java.util.Optional;

public class ShowTable {

    public static void main(String[] args) {
        int nX = 5;
        int nY = 5;

        //var settings=TableSettings.defOfNxNy(nX,nY); //.withNYstart(0).withNYend(1);
        var settings=TableSettings.defOfNxNy(nX,nY)
                .withColNames(Optional.of(new String[]{"A","B","C","D","E"}))
                .withRowNames(Optional.of(new String[]{"r0","r1","r2","r3","r4"}));

        var tableData=TableDataDouble.ofMatAndSettings(createDoubleTableData(nX,nY),settings);
       //var  tableData= TableDataString.ofMat(createStringTableData(nX,nY));
        var tableShower=new TableShower(settings);
        SwingUtilities.invokeLater(() -> tableShower.showTable(tableData));
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
