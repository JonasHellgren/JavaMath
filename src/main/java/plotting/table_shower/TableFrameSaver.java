package plotting.table_shower;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TableFrameSaver {


    public void saveTableFrame(JFrame frame, String pathname, String fileName) throws IOException {
        try {
            frame.setVisible(true);
            BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            frame.paint(g2d);
            g2d.dispose();
            String file = pathname + fileName;
            System.out.println("file = " + file);
            ImageIO.write(image, "png", new File(file));
            System.out.println("Frame contents saved!");
        } catch (IOException  ex) {
            throw  new IOException("IO exception");
        }
    }

}
