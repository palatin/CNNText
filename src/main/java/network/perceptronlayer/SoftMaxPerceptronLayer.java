package network.perceptronlayer;

import helpers.ArrayHelper;

public class SoftMaxPerceptronLayer  extends PerceptronLayer{

    public SoftMaxPerceptronLayer(Neuron[] neurons, double learningRate) {
        super(neurons, learningRate);
    }

    @Override
    protected double[] activate(double[] layerInputs) {


        double[] newValues = new double[layerInputs.length];

        double[] stable = findSubtraction(layerInputs, getMax(layerInputs));



        double sum = ArrayHelper.sum(stable);

        for (int i = 0; i < layerInputs.length; i++) {
            newValues[i] = stable[i] / sum;
        }

        return newValues;
    }




    @Override
    protected double[] activateDerivative(double[] layerInputs) {

        double[] derivatives = new double[layerInputs.length];

        double[] activated = activate(layerInputs);

        for (int i = 0; i < derivatives.length; i++) {
            derivatives[i] = activated[i] * (1-activated[i]);
        }

        return derivatives;
    }

    private double[] findSubtraction(double[] inputs, double max) {

        double[] newValues = inputs.clone();

        for (int i = 0; i < newValues.length; i++) {
            newValues[i] = Math.exp(inputs[i] - max);
        }

        return newValues;
    }

    private double getMax(double[] layerInputs) {
        double max = layerInputs[0];

        for (int i = 0; i < layerInputs.length - 1; i++) {
            if(max < layerInputs[i + 1])
                max = layerInputs[i + 1];
        }

        return max;
    }
}
