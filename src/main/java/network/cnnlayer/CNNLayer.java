package network.cnnlayer;

import exception.CNNLayerException;
import network.cnnlayer.model.FeatureMap;

import java.util.ArrayList;


public abstract class CNNLayer {

    CNNLayer previousLayer;

    CNNLayer nextLayer;

    //Layer's maps
    protected ArrayList<FeatureMap> featureMaps;


    private static final double e  = 2.71828182846;

    public abstract ArrayList<FeatureMap> process(ArrayList<FeatureMap> featureMaps) throws CNNLayerException;


    public void setNextLayer(CNNLayer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void setPreviousLayer(CNNLayer previousLayer) {
        this.previousLayer = previousLayer;
    }

    public abstract double[][] learn(ArrayList<FeatureMap> featureMaps);



    protected double activate(double value) {
        return 1 / (1 + Math.pow(e, -value));
    }

    protected double[][] activate(double[][] values) {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j] = activate(values[i][j]);
            }
        }

        return values;
    }


    protected double activateDerivative(double value) {
        return activate(value)*(1 - activate(value));
    }

    protected double[][] activateDerivative(double[][] values) {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                values[i][j] = activateDerivative(values[i][j]);
            }
        }

        return values;
    }

    public int getMapCounts() {
        return featureMaps.size();
    }

}
