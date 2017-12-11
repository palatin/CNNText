package network.perceptronlayer;

public class Neuron {

    private double[] weights;

    private double input;


    private double error;

    public Neuron() {}

    public Neuron(double[] weights) {
        this.setWeights(weights);
    }

    public double[] getWeights() {


        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public double getInput() {
        return input;
    }


    /**
     * @param learningRate
     * @param outs - outs from previous layer
     * @param inputDer - Derivative input from current neuron
     */
    public void recountWeights(double learningRate, double outs[], double inputDer) {
        for (int i = 0; i < getWeights().length; i++) {
            weights[i] = weights[i] + learningRate * error * inputDer * outs[i];
        }
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }


}
