package adam.monika.adam;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HistogramFrame extends  JPanel{
    public HistogramFrame (File file){
        JFrame f = new JFrame();
        Dimension dimension = getPreferredSize();
        dimension.width = 900;
        dimension.height = 600;
        f.setMinimumSize(dimension);
        f.getContentPane().add(new Histogram("histogram", file));
        f.setVisible(true);

    }
}
