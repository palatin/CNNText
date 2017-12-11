package network.cnnlayer.model;

import network.perceptronlayer.Neuron;

import java.util.ArrayList;

public class FeatureMap {



    private double[][] outputs;

    private double[][] inputs;

    private double[][] errors;

    public FeatureMap() {

    }



    public void setOutputs(double[][] outputs) {
        this.outputs = outputs;
    }

    public double[][] getOutputs() {
        return outputs.clone();
    }



    public static double[][] sum(ArrayList<FeatureMap> featureMaps) {

        double[][] newMapOut = new double[featureMaps.get(0).getOutputs().length][featureMaps.get(0).getOutputs().length];

        for (FeatureMap futureMap: featureMaps) {

            for (int i = 0; i < newMapOut.length; i++) {
                for (int j = 0; j < newMapOut[0].length; j++) {
                    newMapOut[i][j] += futureMap.getOutputs()[i][j];
                }
            }
        }

        return newMapOut;
    }

    public static double[][] sumErrors(ArrayList<FeatureMap> featureMaps) {

        double[][] newMapOut = new double[featureMaps.get(0).getErrors().length][featureMaps.get(0).getErrors().length];

        for (FeatureMap futureMap: featureMaps) {

            for (int i = 0; i < newMapOut.length; i++) {
                for (int j = 0; j < newMapOut[0].length; j++) {
                    newMapOut[i][j] += futureMap.getErrors()[i][j];
                }
            }
        }

        return newMapOut;
    }


    public double[][] getErrors() {
        return errors.clone();
    }

    public void setErrors(double[][] errors) {
        this.errors = errors;
    }

    public double[][] getInputs() {
        return inputs;
    }

    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
    }
}
