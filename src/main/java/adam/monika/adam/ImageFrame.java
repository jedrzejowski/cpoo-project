package adam.monika.adam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFrame extends  JPanel{
    public ImageFrame (BufferedImage img){
        JFrame f = new JFrame();
        Dimension dimension = getPreferredSize();
        dimension.width = 900;
        dimension.height = 600;
        f.setMinimumSize(dimension);
        SetImage image = new SetImage();
        f.getContentPane().add(new JLabel(new ImageIcon(image.setValues(img))));
        f.setVisible(true);

    }
}
