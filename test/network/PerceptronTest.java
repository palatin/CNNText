package network;

import org.junit.Test;

import static org.junit.Assert.*;

public class PerceptronTest {


    @Test
    public void recognize() throws Exception {

        Perceptron perceptron = new Perceptron().builder(0.1)
                .logisticPerceptronLayer(new double[][] {{0.79, 0.85}, {0.44, 0.43}, {0.43, 0.29}}, false)
                .logisticPerceptronLayer(new double[][] {{0.5}, {0.52}}, false)
                .logisticPerceptronLayer(new double[][] {{0}}, false)
                .build();
        double[] probabilities = perceptron.recognize(new double[] {1, 1, 0});

        assertEquals(0.69, probabilities[0], 0.01);
    }

    @Test
    public void learn() throws Exception {

        Perceptron perceptron = new Perceptron().builder(0.1)
                .logisticPerceptronLayer(3, true)
                .logisticPerceptronLayer(2, true)
                .logisticPerceptronLayer(1, false)
                .build();


        perceptron.learn(new double[][] {{0, 0, 0}, {1, 1, 0}, {0, 1, 0}}, new double[][] {{0}, {0}, {0}, {1}}, 8000);
        double[] probabilities = perceptron.recognize(new double[] {1,1,0});
        assertEquals(0, probabilities[0], 0.1);
    }

}