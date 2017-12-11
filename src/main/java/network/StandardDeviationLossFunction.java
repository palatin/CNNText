package network;

import interfaces.LossFunction;

public class StandardDeviationLossFunction implements LossFunction {


    @Override
    public double calculateLoss(double[] actual, double[] exp) {
        double error = 0;

        for (int i = 0; i < actual.length; i++) {
            error += Math.pow(actual[i]  -  exp[i], 2);
        }

        return error/2;
    }


}
