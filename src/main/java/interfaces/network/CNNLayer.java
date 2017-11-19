package interfaces.network;

import exception.CNNLayerException;
import exception.InvalidShapeException;

import java.util.ArrayList;

public interface CNNLayer {




    ArrayList<double[][]> processShapes(ArrayList<double[][]> shapes) throws CNNLayerException;



}
