package network.perceptronlayer;


import helpers.ArrayHelper;



public abstract class PerceptronLayer {


    private Neuron[] neurons;

    private double learningRate;

    private boolean hasBiasNeuron = false;

    PerceptronLayer previousLayer;

    PerceptronLayer nextLayer;

    public PerceptronLayer(Neuron[] neurons, double learningRate, boolean hasBiasNeuron) {
        this.neurons = neurons;
        this.learningRate = learningRate;

        if(hasBiasNeuron) {
            this.hasBiasNeuron = true;
            this.neurons = new Neuron[neurons.length + 1];
            for (int i = 0; i < neurons.length; i++) {
                this.neurons[i] = neurons[i];
            }
            Neuron biasNeuron = new Neuron();
            biasNeuron.setOutput(1);
            this.neurons[neurons.length] = biasNeuron;
        }
    }

    public double[] process(double[] data) {


        for (int i = 0; i < data.length; i++) {
            neurons[i].setOutput(data[i]);
        }




        if(nextLayer == null)
            return data;

        int countInCurrentLayerWithoutBias = neurons.length;

        if(isHasBiasNeuron())
            countInCurrentLayerWithoutBias--;

        int countInNextLayerWithoutBias = nextLayer.neurons.length;

        if(nextLayer.hasBiasNeuron)
            countInNextLayerWithoutBias--;

        double[] outputs = new double[countInNextLayerWithoutBias];

        for (int i = 0; i < countInNextLayerWithoutBias; i++) {

            double[] weights = new double[countInCurrentLayerWithoutBias];

            //take all i's weights from all neurons except bias
            for (int j = 0; j < countInCurrentLayerWithoutBias; j++) {
                weights[j] = neurons[j].getWeights()[i];
            }

            outputs[i] = activate(ArrayHelper.dotProduct(data, weights));
            if(isHasBiasNeuron())
                outputs[i] += activate(ArrayHelper.dotProduct(data, weights) + neurons[neurons.length - 1].getWeights()[i]);
            else
                outputs[i] = activate(ArrayHelper.dotProduct(data, weights));
        }


        return nextLayer.process(outputs);

    }


    public void learn(double[] errors) {



        calculateNewWeightsForNeurons(errors);

        if (previousLayer != null) {

            //calculate errors for each neuron in previous layer
            Neuron[] neurons = previousLayer.neurons;
            double[] prevErrors = new double[neurons.length];

            for (int i = 0; i < neurons.length; i++) {
                prevErrors[i] = ArrayHelper.dotProduct(neurons[i].getWeights(), errors);

            }

            previousLayer.learn(prevErrors);
        }


    }

    private double[] calculateWeightsDelta(double[] errs, double[] outs) {

        double[] deltas = new double[errs.length];

        for (int i = 0; i < deltas.length; i++) {
            deltas[i] =  errs[i] * activateDerivative(outs[i]);
        }

        return deltas;
    }

    private void calculateNewWeightsForNeurons(double[] errors) {
        for (int i = 0; i < neurons.length; i++) {

            Neuron neuron = neurons[i];

            neuron.setError(errors[i]);



            if(nextLayer != null) {
                neuron.recountWeights(learningRate, calculateWeightsDelta(nextLayer.getErrors(), nextLayer.getOuts()));
            }
        }

    }

    protected abstract double activate(double value);

    protected abstract double activateDerivative(double value);




    public void setPreviousLayer(PerceptronLayer previousLayer) {
        this.previousLayer = previousLayer;
    }

    public void setNextLayer(PerceptronLayer nextLayer) {

        this.nextLayer = nextLayer;
        randomizeWeights();


    }

    private void randomizeWeights() {
        //random weights for neurons if not set yet
        for (Neuron neuron : neurons) {

            if (neuron.getWeights() == null) {
                double[] weights = new double[nextLayer.neurons.length];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = Math.random();
                    neuron.setWeights(weights);
                }
            }
        }
    }


    /**
     * @return outputs from all neurons in layer
     */
    private double[] getOuts() {

        double[] outs = new double[neurons.length];

        for (int i = 0; i < outs.length; i++) {
            outs[i] = neurons[i].getOutput();
        }

        return outs;
    }

    /**
     * @return errors from all neurons in layer
     */
    private double[] getErrors() {

        double[] errs = new double[neurons.length];

        for (int i = 0; i < errs.length; i++) {
            errs[i] = neurons[i].getError();
        }

        return errs;
    }


    public boolean isHasBiasNeuron() {
        return hasBiasNeuron;
    }
}
