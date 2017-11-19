package network;

public class CNNWorker {


    /*
    public ArrayList<double[][]> convolution(CNNConvolutionLayer layer) {

        ArrayList<double[][]> shapes = new ArrayList<double[][]>();
        for (int i = 0; i < layer.shapesSize() - 1; i++) {
            double[][] shape = layer.getShape(i);

            for (int filterIndex: layer.getConvCoresIndexes(i)) {
                double[][] convoluteShape = getConvoluteShape(shape, layer.getConvCore(filterIndex));
                shapes.add(convoluteShape);
            }

        }

        return shapes;
    }

    private double[][] getConvoluteShape(double[][] shape, double[][] filter) {

        int length = shape.length - filter.length + 1;
        double[][] newShape = new double[length][length];

        for (int j = 0; j < length; j++) {
            for (int k = 0; k < length; k++) {
                newShape[j][k] = ConvolutionHelper.convolution(shape, filter, j, k);
            }
        }

        return newShape;
    }*/
}
