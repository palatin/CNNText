package network.perceptronlayer;

public class ReLUPerceptronLayer extends PerceptronLayer {


    public ReLUPerceptronLayer(Neuron[] neurons, double learningRate) {
        super(neurons, learningRate);
    }

    @Override
    protected double[] activate(double[] layerInputs) {

        double[] activates = new double[layerInputs.length];

        for (int i = 0; i < activates.length; i++) {
            activates[i] = Math.max(0.01f, layerInputs[i]);
        }

        return activates;
    }

    @Override
    protected double[] activateDerivative(double[] layerInputs) {

        double[] derivatives = new double[layerInputs.length];

        for (int i = 0; i < derivatives.length; i++) {
            derivatives[i] = layerInputs[i] > 0 ? 1:0.01;
        }

        return derivatives;
    }


}
