package network;

import exception.CNNLayerException;
import exception.InvalidShapeException;
import network.model.CNNConvolutionLayer;
import interfaces.network.CNNLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CNNNetwork {

    private final ConvolutionHelper convolutionHelper;

    private ArrayList<CNNLayer> layers;

    private ArrayList<double[][]> shapes;

    @Autowired
    private CNNNetwork(ConvolutionHelper convolutionHelper) {
        layers = new ArrayList<CNNLayer>();
        this.convolutionHelper = convolutionHelper;

        shapes = new ArrayList<>();
    }

    private void forward() {

        layers.forEach(cnnLayer -> {
            try {
                shapes = cnnLayer.processShapes(shapes);
            } catch (CNNLayerException e) {
                e.printStackTrace();
            }
        });
    }



    public CNNNetworkBuilder builder() {
        return new CNNNetwork(convolutionHelper).new CNNNetworkBuilder();
    }

    public class CNNNetworkBuilder {

        private CNNNetworkBuilder() {

        }

        public CNNNetworkBuilder convolutionLayer(ArrayList<double[][]> convCores, int[][] convCoresIndexes) throws InvalidShapeException {

            CNNConvolutionLayer layer = new CNNConvolutionLayer(convCores, convCoresIndexes, convolutionHelper);

            layers.add(layer);

            return this;
        }



        public CNNNetwork build() {


            return CNNNetwork.this;
        }

    }
}
