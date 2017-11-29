package network.cnnlayer;



import exception.CNNLayerException;
import helpers.ConvolutionHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CNNConvolutionLayer extends CNNLayer {

    private ArrayList<double[][]> convCores;


    private final ConvolutionHelper convolutionHelper;

    //indicates pares - map-filter, where rows - maps, columns - number of filter
    private int[][] convCoresIndexes;


    @Autowired
    public CNNConvolutionLayer(ArrayList<double[][]> convCores, int[][] convCoresIndexes, ConvolutionHelper convolutionHelper) {
        this.convCores = convCores;
        this.convCoresIndexes = convCoresIndexes;

        this.convolutionHelper = convolutionHelper;
    }


    private int[] getConvCoresIndexes(int shapeIndex) {
        return convCoresIndexes[shapeIndex];
    }

    private ArrayList<double[][]> convolution(ArrayList<double[][]> shapes) {

        ArrayList<double[][]> newShapes = new ArrayList<>();
        for (int i = 0; i < shapes.size(); i++) {
            double[][] shape = shapes.get(i);

            for (int filterIndex: getConvCoresIndexes(i)) {
                double[][] convoluteShape = getConvoluteShape(shape, convCores.get(filterIndex));
                newShapes.add(convoluteShape);
            }

        }

        return newShapes;
    }

    private double[][] getConvoluteShape(double[][] shape, double[][] filter) {

        int length = shape.length - filter.length + 1;
        double[][] newShape = new double[length][length];

        for (int j = 0; j < length; j++) {
            for (int k = 0; k < length; k++) {
                newShape[j][k] = convolutionHelper.convolution(shape, filter, j, k);
            }
        }

        return newShape;
    }


    public ArrayList<double[][]> processShapes(ArrayList<double[][]> shapes) throws CNNLayerException {

        if(super.nextLayer == null)
            return convolution(shapes);

        return super.nextLayer.processShapes(convolution(shapes));
    }

    @Override
    public void learn(double[] errors) {

    }
}
