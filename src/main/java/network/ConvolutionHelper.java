package network;

public class ConvolutionHelper {


    /**
     * @param shape - shape generally 5x5
     * @param convCore - convolution core same size as shape
     * @return convolution value
     */
    public static int convolution(int[][] shape, int[][] convCore) {
        int size = shape[0].length;
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += shape[i][j] * convCore[i][j];
            }
        }

        return sum;
    }


}
