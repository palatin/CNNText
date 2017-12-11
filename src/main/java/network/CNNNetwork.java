package network;

import exception.CNNLayerException;
import exception.InvalidShapeException;
import helpers.ArrayHelper;
import helpers.ConvolutionHelper;
import helpers.SubSamplingHelper;
import interfaces.ICNNNetworkTrainer;
import interfaces.LossFunction;
import network.cnnlayer.CNNConvolutionLayer;
import network.cnnlayer.CNNLayer;
import network.cnnlayer.CNNPoolingLayer;
import network.cnnlayer.model.CNNObject;
import network.cnnlayer.model.FeatureMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Service
public class CNNNetwork {


    //private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private final char[] classes;

    private double learningRate = 0.1;

    private CNNLayer firstLayer;
    private CNNLayer lastLayer;

    private Perceptron perceptron;

    private LossFunction lossFunction;


    private CNNNetwork(double learningRate, char[] classes, LossFunction lossFunction) {
        this.learningRate = learningRate;
        this.classes = classes;
        this.lossFunction = lossFunction;
    }

    private ArrayList<FeatureMap> forwardThroughConvolution(double[][] shape) throws CNNLayerException {

        ArrayList<FeatureMap> featureMaps = new ArrayList<>();

        FeatureMap featureMap = new FeatureMap();
        featureMap.setOutputs(shape);
        featureMap.setInputs(shape);
        featureMaps.add(featureMap);

        return firstLayer.process(featureMaps);
    }

    private double[] forwardThroughPerceptron(ArrayList<double[][]> shapes) {
        if(perceptron == null)
            autoConfigurePerceptron();

        return perceptron.recognize(ArrayHelper.flatten(shapes));
    }


    public CNNObject recognize(CNNObject object) throws CNNLayerException {

        ArrayList<FeatureMap> featureMaps = forwardThroughConvolution(object.getShape());

        double[] prob = forwardThroughPerceptron(outputsFromFeatureMaps(featureMaps));

        //double[] prob = perceptron.recognize(ArrayHelper.flatten(object.getShape()));

        object.prob = prob;

        object.object = getClassFromProb(prob);


        return object;

    }

    public CNNObject testrecognize(CNNObject object) throws CNNLayerException {


        double[] prob = perceptron.recognize(ArrayHelper.flatten(object.getShape()));

        object.prob = prob;

        object.object = getClassFromProb(prob);


        return object;

    }


    private String getClassFromProb(double[] probs) {

        double max = 0;
        int index = 0;
        for (int i = 0; i < probs.length; i++) {
            if(max < probs[i]) {
                max = probs[i];
                index = i;
            }
        }

        if(index > classes.length)
            return "Unknown class";

        return String.valueOf(classes[index]);

    }

    private ArrayList<double[][]> outputsFromFeatureMaps(ArrayList<FeatureMap> featureMaps) {

        ArrayList<double[][]> shapes = new ArrayList<>(featureMaps.size());

        for (FeatureMap map: featureMaps) {
            shapes.add(map.getOutputs());
        }

        return shapes;
    }
    
    private double[] learn(double[][] shape, double[] exp) throws CNNLayerException {



        ArrayList<FeatureMap> featureMaps = forwardThroughConvolution(shape);

        double[] actualOuts = perceptron.learn(ArrayHelper.flatten(outputsFromFeatureMaps(featureMaps)), exp);

        double[] errors = perceptron.getErrorsFromFirstLayer();

        //Set errors from perceptron
        for (int j = 0; j < featureMaps.size(); j++) {
            featureMaps.get(j).setErrors(new double[][]{{errors[j]}});
        }
        lastLayer.learn(featureMaps);

        return actualOuts;
       // printErrors(lastLayer.learn(featureMaps));


    }

    public void learn(ArrayList<CNNObject> objects, int epoch, ICNNNetworkTrainer trainer) throws CNNLayerException {

        for (int i = 0; i < epoch; i++) {

            double loss = 0;

            long seed = System.nanoTime();
            Collections.shuffle(objects, new Random(seed));

            for (CNNObject object: objects) {


                double[] exp = new double[classes.length + 1];

                exp[object.getClassNumber()] = 1;


                loss = calculateLossFunction(learn(object.getShape(), exp), exp);

            }
            if(trainer != null) {
                trainer.onEpochReady(i + 1, loss);
            }
        }

        if(trainer != null) {
            trainer.onFinish();
        }

    }

    public void testlearn(ArrayList<CNNObject> objects, int epoch, ICNNNetworkTrainer trainer) throws CNNLayerException {



        for (int i = 0; i < epoch; i++) {

            double error = 0;

            long seed = System.nanoTime();
            Collections.shuffle(objects, new Random(seed));
            for (CNNObject object: objects) {


                double[] exp = new double[classes.length + 1];

                exp[object.getClassNumber()] = 1;


                error = calculateLossFunction(perceptron.learn(ArrayHelper.flatten(object.getShape()), exp), exp);

            }
            if(trainer != null) {
                trainer.onEpochReady(i + 1, error);
            }
        }

        if(trainer != null) {
            trainer.onFinish();
        }

    }

    private double calculateLossFunction(double[] actual, double[] exp) {


        return lossFunction.calculateLoss(actual, exp);
    }


    private void autoConfigurePerceptron() {

    }


    /**
     * @param classLabel
     * @return class number for symbol or - 1 if no correct symbol
     */
    public int getClassNumberFromAlphabet(char classLabel) {
        for (int i = 0; i < classes.length; i++) {
            if(classLabel == classes[i])
                return i;
        }

        return -1;
    }




    public static CNNNetworkBuilder builder(ConvolutionHelper convolutionHelper, SubSamplingHelper subSamplingHelper, double learningRate, char[] classes, LossFunction lossFunction) {
        return new CNNNetwork(learningRate, classes, lossFunction).new CNNNetworkBuilder(convolutionHelper, subSamplingHelper);
    }

    @Service
    public class CNNNetworkBuilder {

        private ConvolutionHelper convolutionHelper;
        private SubSamplingHelper subSamplingHelper;


        private CNNNetworkBuilder(ConvolutionHelper convolutionHelper, SubSamplingHelper subSamplingHelper) {
            this.convolutionHelper = convolutionHelper;
            this.subSamplingHelper = subSamplingHelper;
        }



        public CNNNetworkBuilder convolutionLayer(int maps, int filterSize) throws InvalidShapeException {


            CNNConvolutionLayer layer = new CNNConvolutionLayer(maps, filterSize, convolutionHelper, learningRate);

            configureLayer(layer);

            return this;
        }

        public CNNNetworkBuilder poolingLayer() throws InvalidShapeException {

            CNNLayer layer = new CNNPoolingLayer(subSamplingHelper, convolutionHelper);

            configureLayer(layer);

            return this;
        }


        /**Method configure Linked list between layers
         * @param layer - created layer
         */
        private void configureLayer(CNNLayer layer) {

            if(firstLayer == null)
                firstLayer = layer;

            if(lastLayer != null) {
                layer.setPreviousLayer(lastLayer);
                lastLayer.setNextLayer(layer);
            }
            lastLayer = layer;
        }

        public CNNNetwork build() {



            return CNNNetwork.this;
        }


        public CNNNetwork build(Perceptron perceptron) {

            CNNNetwork.this.perceptron = perceptron;

            build();

            return CNNNetwork.this;
        }

    }
}
