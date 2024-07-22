package plotting.table_shower;

import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@AllArgsConstructor
public class FrameAndTableCreator {

    TableSettings settings;

    JFrame createFrame(JTable table) {
        JFrame frame = new JFrame(settings.name());
        int frameHeight = getFrameHeight(table);
        int frameWidth = getFrameWidth(table);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (settings.isScrollPane()) {
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);
        } else {
            frame.add(table);
        }
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
