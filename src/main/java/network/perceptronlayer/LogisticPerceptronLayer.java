package network.perceptronlayer;

public class LogisticPerceptronLayer extends PerceptronLayer {

    private static final double e  = 2.71828182846;

    public LogisticPerceptronLayer(Neuron[] neurons, double learningRate, boolean hasBiasNeuron) {
        super(neurons, learningRate, hasBiasNeuron);
    }


    @Override
    protected double activate(double value) {
        return 1 / (1 + Math.pow(e, -value));
    }

    @Override
    protected double activateDerivative(double value) {
        return activate(value)*(1 - activate(value));
    }
}
