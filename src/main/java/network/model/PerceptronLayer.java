package network.model;

public abstract class PerceptronLayer {

    private double[][] weights;

    private double[] outputs;

    public PerceptronLayer(double[][] weights) {
        this.weights = weights;
    }

    public double[] process(double[] data) {

        for (int i = 0; i < getWeights().length; i++) {
            outputs[i] = activate(dotProduct(data, getWeights()[i]));

        }

        return outputs;

    }

    protected abstract double activate(double value);

    private double dotProduct(double[] x, double[] y) {

        double sum = 0;

        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }

        return sum;
    }

    public double[][] getWeights() {
        return weights;
    }
}
