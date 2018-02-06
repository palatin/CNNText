package network.cnnlayer;



import exception.CNNLayerException;
import helpers.ArrayHelper;
import helpers.ConvolutionHelper;
import network.cnnlayer.model.FeatureMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CNNConvolutionLayer extends CNNLayer {


    private final ConvolutionHelper convolutionHelper;




    private ArrayList<double[][]> filters;


    private double learningRate;

    @Autowired
    public CNNConvolutionLayer(int maps, int filterSize, ConvolutionHelper convolutionHelper, double learningRate) {

        filters = new ArrayList<>();

        //randomize all filters
        for (int i = 0; i < maps; i++) {
            filters.add(convolutionHelper.randomizeFilter(filterSize));
        }


        this.convolutionHelper = convolutionHelper;
        this.learningRate = learningRate;

    }

    @Autowired
    public CNNConvolutionLayer(ArrayList<double[][]> filters, ConvolutionHelper convolutionHelper, double learningRate) {

        this.filters = filters;

        this.convolutionHelper = convolutionHelper;
        this.learningRate = learningRate;

    }



    private ArrayList<FeatureMap> convolution(ArrayList<FeatureMap> featureMaps) {


        ArrayList<FeatureMap> maps = new ArrayList<>(filters.size());


        for (double[][] filter: filters) {

            ArrayList<FeatureMap> temp = new ArrayList<>(super.featureMaps.size());

            for (FeatureMap fm: featureMaps) {
                FeatureMap featureMap = new FeatureMap();

                featureMap.setOutputs(convolutionHelper.getConvoluteShape(fm.getOutputs(), filter, ConvolutionHelper.ConvType.valid));

                temp.add(featureMap);
            }

            FeatureMap featureMap = new FeatureMap();

            featureMap.setInputs(FeatureMap.sum(temp));
            featureMap.setOutputs(activate(featureMap.getInputs()));

            maps.add(featureMap);
        }

        return maps;

    }





    public ArrayList<FeatureMap> process(ArrayList<FeatureMap> maps) throws CNNLayerException {

        this.featureMaps = maps;

        ArrayList<FeatureMap> newMaps = convolution(maps);
        

        if(super.nextLayer == null)
            return newMaps;

        return super.nextLayer.process(newMaps);
    }

    @Override
    public double[][] learn(ArrayList<FeatureMap> featureMaps) {


        calculateErrors(featureMaps);

        recountWeights(featureMaps);

        if(previousLayer != null) {
            return previousLayer.learn(super.featureMaps);
        }

        return super.featureMaps.get(0).getErrors();


    }

    private void calculateErrors(ArrayList<FeatureMap> featureMaps) {


        for (int i = 0; i < super.featureMaps.size(); i++) {


            ArrayList<FeatureMap> temp = new ArrayList<>(featureMaps.size());


            for (int j = 0; j < featureMaps.size(); j++) {
                FeatureMap featureMap = new FeatureMap();
                featureMap.setErrors(ArrayHelper.matrixMultiplication(convolutionHelper.getConvoluteShape(featureMaps.get(j).getErrors(),
                        ArrayHelper.rotateSquareMatrixBy180Degree(filters.get(i)), ConvolutionHelper.ConvType.full),
                        activateDerivative(super.featureMaps.get(i).getInputs())));
                temp.add(featureMap);
            }

            super.featureMaps.get(i).setErrors(FeatureMap.sumErrors(temp));
        }

    }



    private void recountWeights(ArrayList<FeatureMap> maps) {


       double[][] prevLayer = FeatureMap.sum(super.featureMaps);

        for (int i = 0; i < filters.size(); i++) {
            double[][] gradients = ArrayHelper.rotateSquareMatrixBy180Degree(
                    convolutionHelper.getConvoluteShape(prevLayer,
                            ArrayHelper.rotateSquareMatrixBy180Degree(maps.get(i).getErrors()), ConvolutionHelper.ConvType.valid));

            for (int j = 0; j < gradients.length; j++) {
                for (int k = 0; k < gradients[0].length; k++) {
                    filters.get(i)[j][k] = filters.get(i)[j][k] + learningRate*gradients[j][k];
                }
            }
        }

    }





}
