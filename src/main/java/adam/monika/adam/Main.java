package adam.monika.adam;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Main  {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File inputFolder = new File("pic/input");
        File[] listOfImages = inputFolder.listFiles();

        CreateHistogram createHistogram = new CreateHistogram("Histogram obrazu", listOfImages[1]);
        createHistogram.setVisible(true);
       /* for (File file : listOfImages) {
            processImage(file);
        }*/
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


   /*     DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );
        dataset.addValue(histogram.get(5,0).length,"fef", "fe");
        JFreeChart barChart = ChartFactory.createBarChart("Histogram", "Category", "Score", dataset, PlotOrientation.VERTICAL,true,true,false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );*/
        //setContentPane( chartPanel );

    }
}
