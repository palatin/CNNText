package application.controllers;

import exception.CNNLayerException;
import helpers.ImageLoader;
import interfaces.ICNNNetworkTrainer;
import network.CNNNetwork;
import network.cnnlayer.model.CNNObject;

import java.io.File;
import java.util.ArrayList;

public class LearnController extends FileRecognizeController {

    private final CNNNetwork cnnNetwork;


    public LearnController(CNNNetwork cnnNetwork, ImageLoader imageLoader) {

        super(imageLoader);
        this.cnnNetwork = cnnNetwork;
    }



    public void trainNetwork(File folder, int epochs, ICNNNetworkTrainer trainer) {

        ArrayList<CNNObject> objects = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                CNNObject object = prepareObject(fileEntry, cnnNetwork);
                //file can be recognize
                if(object != null)
                    objects.add(object);
            }
        }

        new Thread(() -> {
            try {
                cnnNetwork.testlearn(objects, epochs, trainer);
            } catch (CNNLayerException e) {
                e.printStackTrace();
            }
        }).start();

    }


}
