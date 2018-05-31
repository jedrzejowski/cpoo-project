package adam.monika.adam;

import java.awt.image.BufferedImage;

public class SetImage {



    public BufferedImage setValues(BufferedImage img){

        int[] tab = new int[]{0,255};
        int values[] = Calculate.getValues(img);
        int max = Calculate.getMax(values);
        int min = Calculate.getMin(values);

        int rescaledValues[] = Calculate.scaling(values,min,max,tab);
        int width = img.getWidth();
        int height = img.getHeight();
        img.setRGB(0,0,width,height,rescaledValues, 0b0,width); //chyba trzeba przeliczyć z szarości
        return img;
    }

}
