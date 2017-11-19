package network.model;

public class LogisticPerceptronLayer extends PerceptronLayer {

    private static final double e  = 2.71828182846;

    public LogisticPerceptronLayer(double[][] weights) {
        super(weights);
    }

    @Override
    protected double activate(double value) {
        return 1 / (1 + Math.pow(value, e));
    }
}
