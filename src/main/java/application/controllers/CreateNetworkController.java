package application.controllers;

import application.NetworkConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class CreateNetworkController {


    @FXML
    private TextField learningrateText;

    @FXML
    private TextField neuronsText;

    @FXML
    private TextField filterText;


    public void init() {

    }

    public void onCreateClick(ActionEvent actionEvent) {

        NetworkConfig.LEARNING_RATE = Double.parseDouble(learningrateText.getText());


        NetworkConfig.FILTER_SIZE = Integer.parseInt(filterText.getText());

        NetworkConfig.NEURONS_IN_HIDDEN_LAYER = Integer.parseInt(neuronsText.getText());

        ((Stage) learningrateText.getScene().getWindow()).close();

    }
}
