package plotting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShowTable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Function Table Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            int nX = 10;
            int nY = 10;
            Double[][] data0 = new Double[nX][nY];
            for (int yi = 0; yi < nY; yi++) {
                for (int xi = 0; xi < nY; xi++) {
                    data0[xi][yi] = (double) (xi+yi); // Example function
                }
            }

            // Create table data
            String[] columnNames = new String[11];
            columnNames[0] = "Y \\ X";
            for (int xi = 1; xi <= nX; xi++) {
                columnNames[xi] = "" + (xi - 1);
            }

            Object[][] data = new Object[nY][nX+1];
            for (int yi = 0; yi < nY; yi++) {
                int y0i = nY - yi-1; // Reverse the order of y values
                data[yi][0] = "" + y0i;
                for (int xi = 1; xi <= 10; xi++) {
                    int x0i = xi - 1;
                    data[yi][xi] = String.format("%.2f", (double) data0[x0i][y0i]);  //row idx, col idx
                }
            }

            // Create table model and table
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            Font font = new Font("Serif", Font.PLAIN, 12);
            table.setFont(font);
            table.getTableHeader().setFont(font);
            FontMetrics fontMetrics = table.getFontMetrics(font);
            int rowHeight = fontMetrics.getHeight();
            table.setRowHeight(rowHeight);
            int headerHeight = table.getTableHeader().getPreferredSize().height;
            int tableHeight = headerHeight + ((nY+1) * rowHeight);

            int maxCharsPerCell = 4; // Assuming maximum 6 characters per cell (e.g., "X=10", "Y=10", "0.99")
            int charWidth = fontMetrics.charWidth('W'); // Width of an average character
            int columnWidth = maxCharsPerCell * charWidth;
            int tableWidth = nX * columnWidth;

            // Adding some padding for frame decorations
            int padding = 30;

            int frameHeight = tableHeight + padding;
            int frameWidth = tableWidth + padding;

            // Add table to scroll pane
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Set frame size
            frame.setSize(frameWidth, frameHeight);
            frame.setVisible(true);
        });
    }
}
