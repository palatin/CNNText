package application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public double[][] loadImage(String url) throws IOException {

        BufferedImage img = null;

        img = ImageIO.read(new File(url));

        double[][] arr = new double[img.getWidth()][img.getHeight()];


        for(int i = 0; i < arr.length; i++)
            for(int j = 0; j < arr[0].length; j++)
                arr[i][j] = img.getRGB(i, j);

        return arr;
    }

}
