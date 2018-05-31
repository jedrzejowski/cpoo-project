package adam.monika.adam;

import java.awt.image.BufferedImage;

public class Calculate {


    public static int[] getValues(BufferedImage img) {
         int[] tab = new int[]{0,255};
        int[] values = new int[tab[1]+1];
        int width = img.getWidth();
        int height = img.getHeight();
        int[] Arr;
        Arr =img.getRGB(0,0,width,height,null, 0b0,width);

        for(int i = 0; i< Arr.length; i++){
            int a = ( Arr[i]& 0xFF);
            values[a]++;
        }
        return values;
    }


    public static int getMin(int[] Arr) {
        int min = Arr[0];
        for (int i = 1; i < Arr.length;i++){
            if(Arr[i]<min){
                min = Arr[i];
            }
        }
        return min;
    }

    public static int getMax(int[] Arr) {
        int max = Arr[0];
        for (int i = 1; i < Arr.length;i++){
            if(Arr[i]>max){
                max = Arr[i];
            }
        }
        return max;
    }

    public static int[]  scaling(int[] Arr, int min, int max, int tab[] ) {
        for(int i = 0; i < Arr.length; i++) {
            Arr[i] = ((tab[1] - tab[0]) * (Arr[i]-min)/(max-min)) + tab[0];
        }
        return Arr;
    }


}
