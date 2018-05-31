package adam.monika.adam;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *Tworzy histogram obrazu.
 */
public class Histogram extends JPanel {

    private int[] values;
    private String[] names;
    private String title;
    private int[] tab = new int[]{0,255};
    //private BufferedImage img = null;

    public Histogram(String title, BufferedImage img) {
        this.names = new String[tab[1]+1];
        this.values = new int[tab[1]+1];
        this.title=title;
        for(int i = 0; i< tab[1]+1; i++){
            names[i] =  String.valueOf(i);
        }

        values = Calculate.getValues(img);
    }
/*    private void getValues(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] Arr;
        Arr =img.getRGB(0,0,width,height,null, 0b0,width);

        for(int i = 0; i< Arr.length; i++){
            int a = ((int) Arr[i]& 0xFF);
            values[a]++;
        }
    }*/

    /**
     * Metoda odpowiada za przedstawienie histogramu w formie graficznej
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (values == null || values.length == 0)
            return;
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < values.length; i++) {
            if (minValue > values[i])
                minValue = values[i];
            if (maxValue < values[i])
                maxValue = values[i];
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height;
        int barWidth = clientWidth / values.length;

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int y = titleFontMetrics.getAscent();
        int x = (clientWidth - titleWidth) / 2;
        g.setFont(titleFont);
        g.drawString(title, x, y);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue)
            return;
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);

        for (int i = 0; i < values.length; i++) {

            int valueX = i * barWidth + 1;
            int valueY = top;
            int height = (int) (values[i] * scale);
            if (values[i] >= 0)
                valueY += (int) ((maxValue - values[i]) * scale);
            else {
                valueY += (int) (maxValue * scale);
                height = -height;
            }

            g.setColor(Color.red);
            g.fillRect(valueX, valueY, barWidth - 2, height);
            g.setColor(Color.black);
            g.drawRect(valueX, valueY, barWidth - 2, height);
            int labelWidth = labelFontMetrics.stringWidth(names[i]);
            x = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(names[i], x, y);

        }
    }
}

