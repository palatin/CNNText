package network;

import helpers.ArrayHelper;
import network.perceptronlayer.*;

import java.util.ArrayList;

public class Perceptron {


    private PerceptronLayer firstLayer;
    private PerceptronLayer lastLayer;

    public static enum LayerType {Logistic, SoftMax, RELU}

    public double[] recognize(double[] values) {

        return firstLayer.process(values);

    }

    public void learn(double[][] values, double[][] results, double epoch) {

        double[] actualProb = null;
        for (int i = 0; i < epoch; i++) {

            for (int j = 0; j < values.length; j++) {
                actualProb = firstLayer.process(values[j]);
                lastLayer.learn(findErrors(actualProb, results[j]));
            }


        }

    }

    public double[] learn(double[] values, double[] results) {

        double[] actualProb = recognize(values);

        lastLayer.learn(findErrors(actualProb, results));

        return actualProb;

    }


    public double[] getErrorsFromFirstLayer() {
        return firstLayer.getErrors();
    }

    private double[] findErrors(double[] actual, double[] expected) {

        double[] errors = new double[actual.length];

        for (int i = 0; i < expected.length; i++) {
            errors[i] = expected[i] - actual[i];
        }

        return errors;
    }

    public PerceptronBuilder builder(double learningRate) {
        return new Perceptron().new PerceptronBuilder(learningRate);
    }


    public class PerceptronBuilder {

        private double learningRate;



        public PerceptronBuilder(double learningRate) {
            this.learningRate = learningRate;
        }


        /**
         * @param weights - two dim. array where rows - neuron columns - weights for neuron
         * @param hasBiasNeuron - if true will created "bias neuron"
         * @return
         */
        public PerceptronBuilder logisticPerceptronLayer(double[][] weights,double[][] biasesWeights, boolean hasBiasNeuron) {


            //Neuron[] neurons = neuronsFromWeights(weights);

            //configureLayer(new LogisticPerceptronLayer(neurons, learningRate, hasBiasNeuron));

            return this;
        }

        /**
         * @param count - count of neurons
         * @param bias - count of biases
         * @return
         */
        public PerceptronBuilder perceptronLayer(int count, int bias, LayerType layerType) {


            Neuron[] neurons = new Neuron[count + bias];

            for (int i = 0; i < count; i++) {
                neurons[i] = new Neuron();
            }

            for (int i = 0; i < bias; i++) {
                neurons[i + count] = new BiasNeuron();
            }

            PerceptronLayer perceptronLayer = null;

            switch (layerType) {
                case Logistic: perceptronLayer = new LogisticPerceptronLayer(neurons, learningRate); break;
                case SoftMax: perceptronLayer = new SoftMaxPerceptronLayer(neurons, learningRate); break;
                case RELU: perceptronLayer = new ReLUPerceptronLayer(neurons, learningRate); break;
            }

            configureLayer(perceptronLayer);

            return this;
        }

        private Neuron[] neuronsFromWeights(double[][] weights) {

            Neuron[] neurons = new Neuron[weights.length];

            for (int i = 0; i < neurons.length; i++) {
                neurons[i] = new Neuron(weights[i]);
            }
            return neurons;
        }

        /**Method configure Linked list between layers
         * @param layer - created layer
         */
        private void configureLayer(PerceptronLayer layer) {

            if(firstLayer == null) {
                firstLayer = new InputPerceptronLayer();
                lastLayer = firstLayer;
            }

            if(lastLayer != null) {
                layer.setPreviousLayer(lastLayer);
                lastLayer.setNextLayer(layer);
            }

            lastLayer = layer;
        }

        public Perceptron build() {

            return Perceptron.this;
        }

    }

}
