package network;

import org.junit.Test;

import static org.junit.Assert.*;

public class PerceptronTest {




    @Test
    public void learn() throws Exception {

        Perceptron perceptron = new Perceptron().builder(0.1)
                .perceptronLayer(4, 1, Perceptron.LayerType.RELU)
                .perceptronLayer(1, 0, Perceptron.LayerType.SoftMax)
                .build();


        perceptron.learn(new double[][] {{0, 0, 0}, {0, 1, 0}, {0,0,1}}, new double[][] {{0}, {0}, {1}}, 9000);
        assertEquals(0, perceptron.recognize(new double[] {0, 0, 0})[0], 0.05);
        assertEquals(0, perceptron.recognize(new double[] {0, 1, 0})[0], 0.05);
        assertEquals(1, perceptron.recognize(new double[] {0, 0, 1})[0], 0.05);
    }

}