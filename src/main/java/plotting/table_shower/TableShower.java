package plotting.table_shower;

import lombok.AllArgsConstructor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@AllArgsConstructor
public class TableShower {

    TableSettings settings;
    XYAxisTicksCreator xyAxisTicks;

    public TableShower(TableSettings settings) {
        this.settings = settings;
        this.xyAxisTicks =new XYAxisTicksCreator(settings);
    }

    public void showTable(TableDataI data0) {
        String[] columnNames = xyAxisTicks.columnNames();
        String[] rowNames = xyAxisTicks.createRowNames();
        Object[][] data = createTableData(data0, rowNames);
        var table = createTable(data, columnNames);
        int frameHeight = getFrameHeight(table);
        int frameWidth = getFrameWidth(table);
        var frame = createFrame(table, frameHeight, frameWidth);
        frame.setVisible(true);
    }




    Object[][] createTableData(TableDataI data0, String[] rowNames) {
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



    JFrame createFrame(JTable table, int frameHeight, int frameWidth) {
        JFrame frame = new JFrame(settings.name());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(frameWidth, frameHeight);
        return frame;
    }

    int getFrameWidth(JTable table) {
        int charWidth = table.getFontMetrics(table.getFont()).charWidth('W');
        int columnWidth = settings.maxCharsPerCell() * charWidth;
        int tableWidth = settings.nX() * columnWidth;
        return tableWidth + settings.padding();
    }

    int getFrameHeight(JTable table) {
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        int rowHeight = getHeight(table);
        int tableHeight = headerHeight + ((settings.nY() + 1) * rowHeight);
        return tableHeight + settings.padding();
    }

    JTable createTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        Font font = new Font(settings.fontName(), Font.PLAIN, settings.fontSize());
        table.setFont(font);
        table.getTableHeader().setFont(font);
        int rowHeight = getHeight(table);
        table.setRowHeight(rowHeight);
        return table;
    }

    int getHeight(JTable table) {
        return table.getFontMetrics(table.getFont()).getHeight();
    }


}
