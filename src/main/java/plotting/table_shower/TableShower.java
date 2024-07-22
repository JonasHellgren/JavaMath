package plotting.table_shower;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import javax.swing.*;
import java.io.IOException;

@AllArgsConstructor
public class TableShower {

    public static final int N_COLS_ROW_NAMES = 1;
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

    public void saveTableFrame(JFrame frame,
                               String chartDir,
                               String fileName) throws IOException {
        saver.saveTableFrame(frame,chartDir,fileName);
    }

    // isReverseY()=true => reverse y order, data[yi][..]=..[nY-yi-1] => y min in bottom

    Object[][] createTableData(TableDataI data0, String[] rowNames) {
        Preconditions.checkArgument(settings.isDataOk(data0),
                "nX/ny not equal to nof cols/rows in data");
        Object[][] data = new Object[settings.nY()][settings.nX() + N_COLS_ROW_NAMES];
        for (int yi = 0; yi < settings.nY(); yi++) {
            int y0i = settings.isReverseY() ? settings.nY() - yi - 1:yi;
            data[yi][0] = rowNames[y0i];
            for (int xi = N_COLS_ROW_NAMES; xi <= settings.nX(); xi++) {
                int x0i = xi - N_COLS_ROW_NAMES;
                data[yi][xi] = data0.read(x0i,y0i);
            }
        }
        return data;
    }


}
