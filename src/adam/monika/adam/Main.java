package adam.monika.adam;

import adam.monika.adam.ui.FrameUI;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {

        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {


        File inputFolder = new File("pic/input");
        File[] listOfImages = inputFolder.listFiles();
        BufferedImage img = null;

        //read image
        try {
            img = ImageIO.read(listOfImages[1]);

            new FrameUI(img);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
