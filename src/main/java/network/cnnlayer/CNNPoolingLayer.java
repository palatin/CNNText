package network.cnnlayer;

import exception.CNNLayerException;
import exception.InvalidPoolingWindowException;
import helpers.ArrayHelper;
import helpers.ConvolutionHelper;
import helpers.SubSamplingHelper;
import network.cnnlayer.model.FeatureMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CNNPoolingLayer extends CNNLayer {



    private final SubSamplingHelper subSamplingHelper;

    private ConvolutionHelper convolutionHelper;



    int poolingWindow = 2;

    @Autowired
    public CNNPoolingLayer(SubSamplingHelper subSamplingHelper, ConvolutionHelper convolutionHelper) {
        this.subSamplingHelper = subSamplingHelper;
        this.convolutionHelper = convolutionHelper;

    }

    @Autowired
    public CNNPoolingLayer(ArrayList<double[][]> filters, SubSamplingHelper subSamplingHelper, ConvolutionHelper convolutionHelper) {
        this.subSamplingHelper = subSamplingHelper;
        this.convolutionHelper = convolutionHelper;
        //TODO filters
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

    private double[][] poolMap(FeatureMap map) throws InvalidPoolingWindowException {

        double[][] shape = map.getOutputs();

        int length = shape.length / poolingWindow;

        if(length < 0) //|| length % 2 != 0) //FIXME
            throw new InvalidPoolingWindowException("Pooling window " + poolingWindow + " is invalid for shape with size " + shape.length + "x" + shape.length);

        double[][] newShape = new double[length][length];


        for (int startRow = 0, i=0; i < length; startRow+=poolingWindow, i++) {

            for (int startColumn = 0, j=0; j < length; startColumn+=poolingWindow, j++) {

                newShape[i][j] = subSamplingHelper.subsampling(shape, startRow, startColumn, poolingWindow);

            }

        }

        return newShape;

    }

    @Override
    public ArrayList<FeatureMap> process(ArrayList<FeatureMap> maps) throws CNNLayerException {

        super.featureMaps = maps;

        ArrayList<FeatureMap> newMaps = new ArrayList<>();


        for (int i = 0; i < maps.size(); i++) {

            FeatureMap featureMap = new FeatureMap();
            featureMap.setInputs(poolMap(maps.get(i)));
            featureMap.setOutputs(activate(featureMap.getInputs()));
            newMaps.add(featureMap);
        }

        if(super.nextLayer == null)
            return newMaps;

        return super.nextLayer.process(newMaps);
    }



    private void calculateErrors(ArrayList<FeatureMap> maps) {

        int size = super.featureMaps.get(0).getOutputs().length;
        for (int i = 0; i < maps.size(); i++) {
            super.featureMaps.get(i).setErrors(ArrayHelper.matrixMultiplication(ArrayHelper.upsample(maps.get(i).getErrors(), size), featureMaps.get(i).getOutputs()));
        }

    }

    @Override
    public double[][] learn(ArrayList<FeatureMap> maps) {


        calculateErrors(maps);

        if(super.previousLayer != null) {


            return previousLayer.learn(super.featureMaps);
        }

        return maps.get(0).getErrors();

    }
}
