package interfaces;

import network.cnnlayer.model.CNNObject;

public interface ICNNNetworkTrainer {


    void onEpochReady(int number, double error);

    void onFinish();

}
