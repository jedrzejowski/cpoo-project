package adam.monika.adam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HistogramFrame extends  JPanel{
    public HistogramFrame (BufferedImage img){
        JFrame f = new JFrame();
        Dimension dimension = getPreferredSize();
        dimension.width = 900;
        dimension.height = 600;
        f.setMinimumSize(dimension);
        f.getContentPane().add(new Histogram("histogram", img));
        f.setVisible(true);

    }
}
