package adam.monika.adam;

import org.opencv.core.Core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main  extends JPanel{

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File inputFolder = new File("pic/input");
        File[] listOfImages = inputFolder.listFiles();
        BufferedImage img = null;

        //read image
        try{
            img = ImageIO.read(listOfImages[1]);
        }catch(IOException e){
            System.out.println(e);
        }

        new HistogramFrame(img);

        new ImageFrame(img);
    }


}
