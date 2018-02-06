package application.controllers;

import exception.CNNLayerException;
import helpers.ImageLoader;
import network.CNNNetwork;
import network.cnnlayer.model.CNNObject;

import java.io.File;

public class RecognizeController extends FileRecognizeController {

    private CNNNetwork cnnNetwork;

    public RecognizeController(CNNNetwork cnnNetwork, ImageLoader imageLoader) {
        super(imageLoader);

        this.cnnNetwork = cnnNetwork;
    }

    public CNNObject recognize(File file) throws CNNLayerException {

        CNNObject object = super.prepareObject(file, cnnNetwork);

        if(object != null) {
            return cnnNetwork.testrecognize(object);
        }

        return null;
    }

}
