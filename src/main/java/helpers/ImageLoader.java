package helpers;



import application.NetworkConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static application.NetworkConfig.INPUT_IMAGE_SIZE;

public class ImageLoader {

    public double[][] loadLearnImage(String url) throws IOException {

        BufferedImage img = null;

        img = ImageIO.read(new File(url));


        if(img.getWidth() != INPUT_IMAGE_SIZE || img.getHeight() != INPUT_IMAGE_SIZE)
            img = resize(img, INPUT_IMAGE_SIZE, INPUT_IMAGE_SIZE);



        BufferedImage grayImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = grayImage.getGraphics();
        graphics.drawImage(img, 0, 0, null);

        graphics.dispose();

        double[][] arr = new double[INPUT_IMAGE_SIZE][INPUT_IMAGE_SIZE];


        for(int i = 0; i < arr.length; i++)
            for(int j = 0; j < arr[0].length; j++) {



                arr[i][j] = new Color(img.getRGB(i, j)).getBlue() / 255;
            }


        return arr;
    }

    public double[][][] loadRecognizeImage(String url) throws IOException {

        BufferedImage img = null;

        img = ImageIO.read(new File(url));

        

        BufferedImage grayImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = grayImage.getGraphics();
        graphics.drawImage(img, 0, 0, null);

        graphics.dispose();
        
        int columnCount = grayImage.getWidth() / INPUT_IMAGE_SIZE;

        int rowCount = grayImage.getHeight() / INPUT_IMAGE_SIZE;

        double[][][] arr = new double[rowCount * columnCount][INPUT_IMAGE_SIZE][INPUT_IMAGE_SIZE];

        for (int i = 0; i < rowCount; i++) {
            for (int i1 = 0; i1 < columnCount; i1++) {
                for(int p=0,k = i*INPUT_IMAGE_SIZE; k < i*INPUT_IMAGE_SIZE + INPUT_IMAGE_SIZE; k++, p++) {
                    for (int f=0,l = i1 * INPUT_IMAGE_SIZE; l < i1 * INPUT_IMAGE_SIZE + INPUT_IMAGE_SIZE; l++,f++) {
                        arr[i*i1][p][f] = new Color(img.getRGB(k, l)).getBlue() / 255;
                    }
                }
            }
        }


        return arr;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
