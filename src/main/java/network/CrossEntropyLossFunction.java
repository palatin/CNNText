package network;

import interfaces.LossFunction;

public class CrossEntropyLossFunction implements LossFunction {


    @Override
    public double calculateLoss(double[] actual, double[] exp) {

        double error = 0;

        for (int i = 0; i < actual.length; i++) {
            if(actual[i] != 0)
                error += -Math.log(actual[i]) * exp[i];
        }

        return error;
    }

}
