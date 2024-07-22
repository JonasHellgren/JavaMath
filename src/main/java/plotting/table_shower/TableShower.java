package plotting.table_shower;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import javax.swing.*;
import java.io.IOException;

@AllArgsConstructor
public class TableShower {

    TableSettings settings;
    XYAxisTicksCreator xyAxisTicks;
    FrameAndTableCreator creator;
    TableFrameSaver saver;

    public TableShower(TableSettings settings) {
        this.settings = settings;
        this.xyAxisTicks =new XYAxisTicksCreator(settings);
        this.creator=new FrameAndTableCreator(settings);
        this.saver=new TableFrameSaver();
    }

    public void showTable(TableDataI data0) {
        JFrame frame = createTableFrame(data0);
        frame.setVisible(true);
    }

    public JFrame createTableFrame(TableDataI data0) {
        String[] columnNames = xyAxisTicks.columnNames();
        String[] rowNames = xyAxisTicks.createRowNames();
        Object[][] data = createTableData(data0, rowNames);
        var table = creator.createTable(data, columnNames);
        return creator.createFrame(table);
    }

    public void saveTableFrame(JFrame frame, String chartDir, String fileName) throws IOException {
        saver.saveTableFrame(frame,chartDir,fileName);
    }

    Object[][] createTableData(TableDataI data0, String[] rowNames) {
        Preconditions.checkArgument(settings.isDataOk(data0),"nX/ny not equal to nof cols/rows in data");
        Object[][] data = new Object[settings.nY()][settings.nX() + 1];
        for (int yi = 0; yi < settings.nY(); yi++) {
            int y0i = settings.nY() - yi - 1; // Reverse the order of y values
            data[yi][0] = rowNames[yi];
            for (int xi = 1; xi <= settings.nX(); xi++) {
                int x0i = xi - 1;
                data[yi][xi] = data0.read(x0i,y0i);
            }
        }
        return data;
    }


}
