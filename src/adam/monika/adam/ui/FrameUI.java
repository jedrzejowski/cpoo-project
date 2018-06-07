package adam.monika.adam.ui;

import adam.monika.adam.calc.Histogram;
import com.emxsys.chart.extension.LogarithmicAxis;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class FrameUI extends Stage {

    GridPane root;
    AreaChart<Number, Number> chart;
    ImageView imageView1, imageView2;

    public FrameUI(BufferedImage image1) throws IOException {

        image1 = Histogram.getGrayscaleImage(image1);

        root = FXMLLoader.load(getClass().getResource("FrameUI.fxml"));
        imageView1 = (ImageView) root.lookup("#image1");
        imageView2 = (ImageView) root.lookup("#image2");

        int[] histogram1 = Histogram.getHistogram(image1);
        imageView1.setImage(SwingFXUtils.toFXImage(image1, null));

        int[] lut = Histogram.equalize(histogram1, image1.getHeight() * image1.getWidth());
        BufferedImage image2 = Histogram.lutToImage(image1, lut);

        int[] histogram2 = Histogram.getHistogram(image2);
        imageView2.setImage(SwingFXUtils.toFXImage(image2, null));

        //region Wykres

        int max = 0;
        for (int i = 0, t; i < 256; i++) {
            t = Math.max(histogram1[i], histogram2[i]);
            max = Math.max(t, max);
        }

        NumberAxis xAxis = new NumberAxis(-10, 265, 32);
        LogarithmicAxis yAxis = new LogarithmicAxis(1, max);
        chart = new AreaChart<>(xAxis, yAxis);
        chart.setTitle("Histogram");

        XYChart.Series series1 = Histogram.histogramToChartSeries(histogram1);
        series1.setName("Orginalny");
        XYChart.Series series2 = Histogram.histogramToChartSeries(histogram2);
        series2.setName("Unormowany");

        chart.getData().addAll(series1, series2);

        GridPane.setColumnIndex(chart, 1);
        GridPane.setRowIndex(chart, 0);
        GridPane.setRowSpan(chart, 2);

        root.getChildren().add(chart);

        //endregion


        initModality(Modality.APPLICATION_MODAL);
        setTitle("Tytuł");
        setScene(new Scene(root));
        show();
    }
}
