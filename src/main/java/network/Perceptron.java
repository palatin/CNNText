package network;

import helpers.ArrayHelper;
import network.model.LogisticPerceptronLayer;
import network.model.PerceptronLayer;

import java.util.ArrayList;

public class Perceptron {

    private ArrayList<PerceptronLayer> layers;

    public double[] recognize(ArrayList<double[][]> shapes) {

        //last layer - indicate number of classes
        double[] probabilities = new double[layers.get(layers.size() - 1).getWeights().length];


        probabilities = ArrayHelper.flatten(shapes);

        //TODO

        return probabilities;

    }

    public PerceptronBuilder builder() {
        return new Perceptron().new PerceptronBuilder();
    }


    public class PerceptronBuilder {


        public PerceptronBuilder logisticPerceptronLayer(double[][] weights) {

            layers.add(new LogisticPerceptronLayer(weights));

            return this;
        }

        public Perceptron build() {

            return Perceptron.this;
        }

    }

}
