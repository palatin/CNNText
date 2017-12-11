package application;

import exception.CNNLayerException;
import exception.InvalidShapeException;
import network.CNNNetwork;
import network.Perceptron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GUI {



    public static void main(String[] args) {

        ImageLoader imageLoader = new ImageLoader();

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CNNNetwork.CNNNetworkBuilder builder = context.getBean( CNNNetwork.CNNNetworkBuilder.class);

        try {
            double[][] image = imageLoader.loadImage("C:\\Users\\Игорь\\Google Диск\\5 course\\5 course\\Курсовая вычис интеллект\\trains\\0\\0-1.png");

            Perceptron perceptron = new Perceptron().builder(0.1)
                    .logisticPerceptronLayer(120, false)
                    .logisticPerceptronLayer(75, true)
                    .logisticPerceptronLayer(31,false)
                    .build();

            CNNNetwork network = builder.convolutionLayer(6,5)
                    .poolingLayer()
                    .convolutionLayer(16,5)
                    .poolingLayer()
                    .convolutionLayer(120,5)
                    .build(perceptron);


            //double[] probs = network.recognize(image);

            /*for (double prob : probs) {
                System.out.println(prob);
            }*/
            double[] exp = new double[31];

            for (int i = 0; i < exp.length; i++) {
                if(i == 1)
                    exp[i] = 1;
                else
                    exp[i] = 0;
            }

            network.learn(image, exp, 1);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidShapeException e) {
            e.printStackTrace();
        } catch (CNNLayerException e) {
            e.printStackTrace();
        }
    }

}
