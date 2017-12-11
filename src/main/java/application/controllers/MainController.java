package application.controllers;

import application.AppConfig;
import exception.CNNLayerException;
import helpers.ImageLoader;
import interfaces.ICNNNetworkTrainer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import network.CNNNetwork;
import network.cnnlayer.model.CNNObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static application.NetworkConfig.EPOCHS_COUNT;

public class MainController implements Initializable {


    private LearnController learnController;

    private RecognizeController recognizeController;

    private CNNNetwork cnnNetwork;

    private Canvas canvas;


    @FXML
    private AnchorPane anchorPaneCanvas;

    @FXML
    private Label recognizedObjectLabel;

    @FXML
    private Label networkStatus;



    @FXML
    private Label epochCountLabel;

    @FXML
    private Button createNetworkButton;

    @FXML
    private Button learnNetworkButton;

    @FXML
    private Button recognizeButton;

    @FXML
    private LineChart errorChart;



    private XYChart.Series<String, Double> errorData;


    public MainController() {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        canvas = new Canvas(anchorPaneCanvas.getWidth(), anchorPaneCanvas.getHeight());

        anchorPaneCanvas.getChildren().add(canvas);

        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
        errorData = new XYChart.Series<>();
        errorData.setName("Похибка");
        data.addAll(errorData);

        errorChart.setData(data);
    }

    private void initNetwork() {

        learnNetworkButton.setDisable(false);

        recognizeButton.setDisable(false);

        createNetworkButton.setDisable(true);


        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        learnController = new LearnController(cnnNetwork, context.getBean(ImageLoader.class));

        recognizeController = new RecognizeController(cnnNetwork, context.getBean(ImageLoader.class));

        networkStatus.setText("Статус мережі: " + "готова");
    }


    public void onLearnButtonClick(ActionEvent actionEvent) throws CNNLayerException {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        File directory = directoryChooser.showDialog(null);

        if(directory != null) {




            learnController.trainNetwork(directory, EPOCHS_COUNT, new ICNNNetworkTrainer() {
                @Override
                public void onEpochReady(int number, double error) {
                    Platform.runLater(() ->
                    {
                        System.out.println("Loss function " + error);
                        epochCountLabel.setText("epochs: " + number + "/" + EPOCHS_COUNT);
                        errorData.getData().add(new XYChart.Data<>(String.valueOf(number), error));

                    });
                }

                @Override
                public void onFinish() {
                    Platform.runLater(() -> epochCountLabel.setText(""));
                }
            });
        }

    }

    public void displayResult(CNNObject cnnObject) {

        if(cnnObject == null) {
            return;
        }

        System.out.println(Arrays.toString(cnnObject.prob));

        recognizedObjectLabel.setText(cnnObject.object);


    }

    public void onRecognizeClick(ActionEvent actionEvent) throws CNNLayerException {


        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(null);

        if(file != null) {
            displayResult(recognizeController.recognize(file));
        }
    }



    public void onCreateNetworkClick(ActionEvent actionEvent) throws IOException {



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("create_network.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Create network");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(loader.load());
        dialogStage.setScene(scene);

        dialogStage.showAndWait();


        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


        cnnNetwork = context.getBean(CNNNetwork.class);

        initNetwork();


    }
}
