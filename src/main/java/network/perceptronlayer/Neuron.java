package network.perceptronlayer;

public class Neuron {

    private double[] weights;

    private double output;




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

    public void setOutput(double output) {
        this.output = output;
    }

    public double getOutput() {
        return output;
    }



    public void recountWeights(double learningRate, double deltas[]) {
        for (int i = 0; i < getWeights().length; i++) {
            getWeights()[i] = getWeights()[i] + output * deltas[i] * learningRate;
        }
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }


}
