package adam.monika.adam.calc;

import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Histogram {

    public static BufferedImage getGrayscaleImage(BufferedImage colorImage) {
        BufferedImage grayImage = new BufferedImage(
                colorImage.getWidth(),
                colorImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster colorRaster = colorImage.getRaster();
        WritableRaster grayRaster = grayImage.getRaster();

        for (int i = 0; i < colorRaster.getWidth(); i++) {
            for (int j = 0; j < colorRaster.getHeight(); j++) {
                grayRaster.setSample(i, j, 0, colorRaster.getSample(i, j, 0));
            }
        }

        grayImage.setData(grayRaster);

        return grayImage;
    }

    public static int[] getHistogram(BufferedImage image) {
        WritableRaster wr = image.getRaster();
        int width = image.getWidth(), height = image.getHeight();

        int[] histogram = new int[256];

        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int pixel = wr.getSample(x, y, 0);
                histogram[pixel]++;
            }
        }

        return histogram;
    }

    public static int[] equalize(int[] histogram, int hTw) {
        float temp;

        int[] chistogram = new int[256];
        chistogram[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            chistogram[i] = chistogram[i - 1] + histogram[i];
        }

        int[] lut = new int[256];
        for (int i = 0; i < 256; i++) {
            temp = (float) ((chistogram[i] * 255.0) / (float) hTw);
            lut[i] = (int) temp;
        }

        return lut;
    }

    public static BufferedImage lutToImage(BufferedImage oldImage, int[] lut) {
        BufferedImage newImage = new BufferedImage(
                oldImage.getWidth(),
                oldImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster oldRaster = oldImage.getRaster(),
                newRaster = newImage.getRaster();

        int width = newImage.getWidth(), height = newImage.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = lut[oldRaster.getSample(x, y, 0)];
                newRaster.setSample(x, y, 0, pixel);
            }
        }

        newImage.setData(newRaster);

        return newImage;
    }

    public static XYChart.Series histogramToChartSeries(int[] histogram) {

        XYChart.Series series = new XYChart.Series();
        ObservableList data = series.getData();

        for (int i = 0; i < histogram.length; i++) {
            int val = histogram[i];
            if (val == 0) val = 1;
            data.add(new XYChart.Data(i, val));
        }

        return series;
    }
}
