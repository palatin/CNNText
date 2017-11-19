package network.model;

import exception.CNNLayerException;
import exception.InvalidPoolingWindowException;
import interfaces.network.CNNLayer;
import helpers.SubSamplingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CNNPoolingLayer implements CNNLayer {



    private final SubSamplingHelper subSamplingHelper;

    int poolingWindow = 2;

    @Autowired
    public CNNPoolingLayer(SubSamplingHelper subSamplingHelper) {
        this.subSamplingHelper = subSamplingHelper;
    }

    /**
     * @param subSamplingHelper
     * @param poolingWindow - a group of pixels (usually 2 × 2 size) is compressed to one pixel
     */
    @Autowired
    public CNNPoolingLayer(SubSamplingHelper subSamplingHelper, int poolingWindow) {
        this.subSamplingHelper = subSamplingHelper;

        this.poolingWindow = poolingWindow;
    }

    private double[][] poolShape(double[][] shape) throws InvalidPoolingWindowException {

        int length = shape.length / poolingWindow;

        if(length < 0 || length % 2 != 0)
            throw new InvalidPoolingWindowException("Pooling window " + poolingWindow + "is invalid for shape with size " + shape.length + "x" + shape.length);

        double[][] newShape = new double[length][length];


        for (int startRow = 0, i=0; i < length; startRow+=poolingWindow, i++) {

            for (int startColumn = 0, j=0; j < length; startColumn+=poolingWindow, j++) {

                newShape[i][j] = subSamplingHelper.subsampling(shape, startRow, startColumn, poolingWindow);
            }

        }

        return newShape;

    }

    @Override
    public ArrayList<double[][]> processShapes(ArrayList<double[][]> shapes) throws CNNLayerException {

        ArrayList<double[][]> newShapes = new ArrayList<>();

        for (double[][] shape: shapes) {
            newShapes.add(poolShape(shape));
        }


        shapes = null;

        return newShapes;
    }
}
