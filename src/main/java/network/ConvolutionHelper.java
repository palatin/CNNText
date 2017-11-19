package network;

public class ConvolutionHelper {


    /**
     * @param shape - shape generally 5x5
     * @param convCore - convolution core same size as shape
     * @return convolution value
     */
    public double convolution(double[][] shape, double[][] convCore) {
        int size = shape[0].length;
        double sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += shape[i][j] * convCore[i][j];
            }
        }

        return sum;
    }


    /**use for sliding window method
     * @param shape - shape generally 5x5
     * @param convCore - convolution core same size as shape
     * @param startRow - current row index
     * @param startColumn - current column index
     * @return convolution value
     */
    public double convolution(double[][] shape, double[][] convCore, int startRow, int startColumn) {

        double sum = 0;
        int size = convCore[0].length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += shape[i + startRow][j + startColumn] * convCore[i][j];
            }
        }

        return sum;
    }
}
