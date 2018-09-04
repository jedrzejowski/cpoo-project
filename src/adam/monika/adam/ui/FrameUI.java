package adam.monika.adam.ui;

import adam.monika.adam.calc.Histogram;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class FrameUI extends Stage {

    GridPane root;
    Label label;
    AreaChart<Number, Number> chart;
    ImageView imageView1, imageView2, imageView3;

    public FrameUI(BufferedImage image1) throws IOException {

        image1 = Histogram.getGrayscaleImage(image1);

        root = FXMLLoader.load(getClass().getResource("FrameUI.fxml"));
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        imageView1 = (ImageView) root.lookup("#image1");
        imageView2 = (ImageView) root.lookup("#image2");
        imageView3 = (ImageView) root.lookup("#image3");
        label = (Label) root.lookup("#label");

        int[] histogram1 = Histogram.getHistogram(image1);
        imageView1.setImage(SwingFXUtils.toFXImage(image1, null));

        int[] lut = Histogram.equalize(histogram1, image1.getHeight() * image1.getWidth());
        BufferedImage image2 = Histogram.lutToImage(image1, lut);

        // Histogram globalny

        int[] histogram2 = Histogram.getHistogram(image2);
        imageView2.setImage(SwingFXUtils.toFXImage(image2, null));

        //Histogram lokalny

        BufferedImage image3 = Histogram.localEqualizeHistogram(image1, 16);
        int[] histogram3 = Histogram.getHistogram(image3);
        imageView3.setImage(SwingFXUtils.toFXImage(image3, null));


        //region Wykres

        int max = 0;
        for (int i = 0, t; i < 256; i++) {
            t = Math.max(histogram1[i], histogram2[i]);
            max = Math.max(t, max);
        }

        NumberAxis xAxis = new NumberAxis(-10, 265, 32);
        NumberAxis yAxis = new NumberAxis();
        chart = new AreaChart<>(xAxis, yAxis);
        chart.setTitle("Histogram");

        XYChart.Series series1 = Histogram.histogramToChartSeries(histogram1);
        series1.setName("Orginalny");
        XYChart.Series series2 = Histogram.histogramToChartSeries(histogram2);
        series2.setName("Unormowany globalny");
        XYChart.Series series3 = Histogram.histogramToChartSeries(histogram3);
        series3.setName("Unormowany lokalny");

        chart.getData().addAll(series1, series2, series3);

        chart.setCreateSymbols(false);

        GridPane.setColumnIndex(chart, 2);
        GridPane.setRowIndex(chart, 0);
        GridPane.setRowSpan(chart, 2);

        root.getChildren().add(chart);

        //endregion

        label.setText("Autorzy:\n" +
                " - Adam Jędrzejowski\n" +
                " - Adam Borkowski\n" +
                " - Monika Dwojak\n" +
                "\n" +
                "Program przedstawia dwie różne\nmożliwości unormowania histogramu\n" +
                "globalną(lewo dół) i lokalną(prawo dół)" +
                "\n");


        initModality(Modality.APPLICATION_MODAL);
        setTitle("Tytuł");
        setScene(new Scene(root));
        show();
        
        //Zapisanie obrazów
    }
}
