package network.perceptronlayer;

public class InputPerceptronLayer extends PerceptronLayer {



    public InputPerceptronLayer() {
        super(null, 0);
    }


    @Override
    public double[] process(double[] data) {

        super.neurons = new Neuron[data.length];

        for (int i = 0; i < data.length; i++) {
            Neuron neuron = new Neuron();
            neuron.setInput(data[i]);
            super.neurons[i] = neuron;
        }

        return super.nextLayer.process(data);
    }

    @Override
    protected double[] activate(double[] layerInputs) {
        return layerInputs.clone();
    }

    @Override
    protected double[] activateDerivative(double[] layerInputs) {
        return layerInputs.clone();
    }
}
