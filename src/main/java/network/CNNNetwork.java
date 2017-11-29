package network;

import exception.CNNLayerException;
import exception.InvalidShapeException;
import helpers.ArrayHelper;
import helpers.ConvolutionHelper;
import helpers.SubSamplingHelper;
import network.cnnlayer.CNNConvolutionLayer;
import network.cnnlayer.CNNLayer;
import network.cnnlayer.CNNPoolingLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CNNNetwork {


    private CNNLayer firstLayer;
    private CNNLayer lastLayer;

    private Perceptron perceptron;


    private CNNNetwork() {

    }

    private ArrayList<double[][]> forwardThroughConvolution(double[][] shape) throws CNNLayerException {

        ArrayList<double[][]> shapes = new ArrayList<>();

        shapes.add(shape);

        return firstLayer.processShapes(shapes);
    }

    private double[] forwardThroughPerceptron(ArrayList<double[][]> shapes) {
        if(perceptron == null)
            autoConfigurePerceptron();

        return perceptron.recognize(ArrayHelper.flatten(shapes));
    }


    public double[] recognize(double[][] shape) throws CNNLayerException {

        return forwardThroughPerceptron(forwardThroughConvolution(shape));

    }

    private void autoConfigurePerceptron() {

    }



    @Autowired
    public static CNNNetworkBuilder builder(ConvolutionHelper convolutionHelper, SubSamplingHelper subSamplingHelper) {
        return new CNNNetwork().new CNNNetworkBuilder(convolutionHelper, subSamplingHelper);
    }

    @Service
    public class CNNNetworkBuilder {

        private ConvolutionHelper convolutionHelper;
        private SubSamplingHelper subSamplingHelper;

        private CNNNetworkBuilder(ConvolutionHelper convolutionHelper, SubSamplingHelper subSamplingHelper) {
            this.convolutionHelper = convolutionHelper;
            this.subSamplingHelper = subSamplingHelper;
        }

        public CNNNetworkBuilder convolutionLayer(ArrayList<double[][]> convCores, int[][] convCoresIndexes) throws InvalidShapeException {


            CNNConvolutionLayer layer = new CNNConvolutionLayer(convCores, convCoresIndexes, convolutionHelper);

            configureLayer(layer);

            return this;
        }

        public CNNNetworkBuilder poolingLayer() throws InvalidShapeException {

            CNNLayer layer = new CNNPoolingLayer(subSamplingHelper);

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
            return CNNNetwork.this;
        }

    }
}
