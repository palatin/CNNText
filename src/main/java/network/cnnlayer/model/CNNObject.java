package network.cnnlayer.model;

public class CNNObject {

    private int classNumber;

    private double[][] shape;

    public double[] prob;

    public String object;

    public CNNObject(double[][] shape, int classNumber) {
        this.shape = shape;

        this.classNumber = classNumber;
    }

    public double[][] getShape() {
        return shape;
    }

    public int getClassNumber() {
        return classNumber;
    }
}
