package adam.monika.adam;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class CreateHistogram extends JPanel {


    public CreateHistogram(String title, File file) {

        Mat imageMat = Imgcodecs.imread(file.getPath());
        ArrayList<Mat> list = new ArrayList<Mat>();
        list.add(imageMat);
        Mat histogram = new Mat();
        MatOfInt histSize = new MatOfInt(256);
        final MatOfFloat histRange = new MatOfFloat(0f, 256f);


        Imgproc.calcHist(list, new MatOfInt(1), new Mat(), histogram, histSize, histRange, false);
        // System.out.println(histogram.dump());

        Core.normalize(histogram, histogram, histSize.height(), 0, Core.NORM_INF);
        int binWidth = 5;
        Scalar[] colorsRgb = new Scalar[]{new Scalar(200, 0, 0, 255), new Scalar(0, 200, 0, 255), new Scalar(0, 0, 200, 255)};
        Mat histMatBitmap = new Mat(histSize.height(), histSize.width(), histogram.type());
        for (int j = 0; j < 256; j++) {
            Point p1 = new Point(binWidth * (j - 1), histSize.height() - Math.round(histogram.get(j - 1, 0)[0]));
            Point p2 = new Point(binWidth * j, histSize.height() - Math.round(histogram.get(j, 0)[0]));
            Imgproc.line(histMatBitmap, p1, p2, colorsRgb[2], 2, 8, 0);
        }

        Image bufImage = ConvertMat2Image(histMatBitmap);
        displayImage(bufImage);


    }


    private static BufferedImage ConvertMat2Image(Mat imgContainer) {
        MatOfByte byteMatData = new MatOfByte();
        //image formatting
        Imgcodecs.imencode(".jpg", imgContainer, byteMatData);
        // Convert to array
        byte[] byteArray = byteMatData.toArray();
        BufferedImage img = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            //load image
            img = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return img;
    }

    public void displayImage(Image img2) {

        ImageIcon icon = new ImageIcon(img2);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        icon.setImage(img2);

        lbl.setIcon(icon);
        frame.add(lbl);
        Dimension dimension = getPreferredSize();
        dimension.width = 900;
        dimension.height = 600;
        frame.setSize(dimension);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

