package network.perceptronlayer;


import helpers.ArrayHelper;
import helpers.ConvolutionHelper;


public abstract class PerceptronLayer {


    Neuron[] neurons;

    private double learningRate;



    PerceptronLayer previousLayer;

    PerceptronLayer nextLayer;

    public PerceptronLayer(Neuron[] neurons, double learningRate) {
        this.neurons = neurons;
        this.learningRate = learningRate;


    }

    public double[] process(double[] data) {

        randomizeWeights(data.length);

        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setInput(ArrayHelper.dotProduct(data, neurons[i].getWeights()));
        }


        if(nextLayer == null)
            return activate(getInputs());


        return nextLayer.process(activate(getInputs()));

    }


    public double[] learn(double[] errors) {


        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setError(errors[i]);
        }

        if (previousLayer != null) {

            //calculate errors for each neuron in previous layer
            double[] prevErrors = new double[previousLayer.neurons.length];



            for (int i = 0; i < prevErrors.length; i++) {

                for (int i1 = 0; i1 < neurons.length; i1++) {
                    prevErrors[i] += neurons[i1].getWeights()[i] * errors[i1];
                }

            }
            calculateNewWeightsForNeurons();

            return previousLayer.learn(prevErrors);
        }

        return errors;
    }



    private void calculateNewWeightsForNeurons() {

        double[] inputs = previousLayer.activate(previousLayer.getInputs());

        double[] derivatives = activateDerivative(getInputs());

        for (int i = 0; i < neurons.length; i++) {

            Neuron neuron = neurons[i];


            if(previousLayer != null) {
                neuron.recountWeights(learningRate, inputs, derivatives[i]);
            }
        }

    }



    protected abstract double[] activate(double[] layerInputs);




    protected abstract double[] activateDerivative(double[] layerInputs);




    public void setPreviousLayer(PerceptronLayer previousLayer) {
        this.previousLayer = previousLayer;
    }

    public void setNextLayer(PerceptronLayer nextLayer) {

        this.nextLayer = nextLayer;

    }

    private void randomizeWeights(int length) {
        //random weights for neurons if not set yet
        for (Neuron neuron : neurons) {


            if (neuron.getWeights() == null) {
                double[] weights = new double[length];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = Math.random() + 0.01;
                }
                neuron.setWeights(weights);
            }
        }
    }


    /**
     * @return outputs from all neurons in layer
     */
    private double[] getInputs() {

        double[] outs = new double[neurons.length];

        for (int i = 0; i < outs.length; i++) {
            outs[i] = neurons[i].getInput();
        }

        return outs;
    }

    /**
     * @return errors from all neurons in layer
     */
    public double[] getErrors() {

        double[] errs = new double[neurons.length];

        for (int i = 0; i < errs.length; i++) {
            errs[i] = neurons[i].getError();
        }

        return errs;
    }


}
