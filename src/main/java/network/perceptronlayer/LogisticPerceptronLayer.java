package network.perceptronlayer;

public class LogisticPerceptronLayer extends PerceptronLayer {

    private static final double e  = 2.71828182846;

    public LogisticPerceptronLayer(Neuron[] neurons, double learningRate) {
        super(neurons, learningRate);
    }



    private double activate(double value) {
        return 1 / (1 + Math.pow(e, -value));
    }

    @Override
    protected double[] activate(double[] layerInputs) {

        double[] newValuers = new double[layerInputs.length];

        for (int i = 0; i < newValuers.length; i++) {
            newValuers[i] = activate(layerInputs[i]);
        }

        return newValuers;

    }

    @Override
    protected double[] activateDerivative(double[] layerInputs) {

        double[] newValuers = new double[layerInputs.length];

        for (int i = 0; i < newValuers.length; i++) {
            newValuers[i] = activate(layerInputs[i])*(1 - activate(layerInputs[i]));
        }

        return newValuers;

    }

}
