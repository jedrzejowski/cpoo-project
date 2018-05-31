package adam.monika.adam;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Main  extends JPanel{

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File inputFolder = new File("pic/input");
        File[] listOfImages = inputFolder.listFiles();

        new HistogramFrame(listOfImages[1]);

    }

    private static void processImage(File file) {
        System.out.println(file.getPath());
        Mat imageMat = Imgcodecs.imread(file.getPath());

        System.out.println(imageMat.channels());

        ArrayList<Mat> list = new ArrayList<Mat>();
        list.add(imageMat);

        Mat histogram = new Mat();
        MatOfInt histSize = new MatOfInt(256);
        final MatOfFloat histRange = new MatOfFloat(0f, 256f);

        Imgproc.calcHist(list, new MatOfInt(1), new Mat(), histogram, histSize, histRange,false);
        System.out.println(histogram.dump());

    }
}
