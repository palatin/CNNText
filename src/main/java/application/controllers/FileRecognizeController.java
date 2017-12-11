package application.controllers;

import helpers.ImageLoader;
import network.CNNNetwork;
import network.cnnlayer.model.CNNObject;

import java.io.File;
import java.io.IOException;

public class FileRecognizeController {

    private ImageLoader imageLoader;

    public FileRecognizeController(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    protected CNNObject prepareObject(File file, CNNNetwork cnnNetwork) {

        int classNumber = cnnNetwork.getClassNumberFromAlphabet(file.getName().charAt(0));

        //file not match to any classes
        if(classNumber == -1)
            return null;

        try {

            return new CNNObject(imageLoader.loadLearnImage(file.getAbsolutePath()), classNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
