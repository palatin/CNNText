package network.cnnlayer;

import exception.CNNLayerException;

import java.util.ArrayList;

public abstract class CNNLayer {

    CNNLayer previousLayer;

    CNNLayer nextLayer;




    public abstract ArrayList<double[][]> processShapes(ArrayList<double[][]> shapes) throws CNNLayerException;


    public void setNextLayer(CNNLayer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void setPreviousLayer(CNNLayer previousLayer) {
        this.previousLayer = previousLayer;
    }

    public abstract void learn(double[] errors);

}
