package helpers;





public class ConvolutionHelper {

    public enum ConvType {full,valid}



    public static double[][] convnFull(double[][] matrix,
                                       final double[][] kernel) {
        int m = matrix.length;
        int n = matrix[0].length;
        final int km = kernel.length;
        final int kn = kernel[0].length;

        final double[][] extendMatrix = new double[m + 2 * (km - 1)][n + 2
                * (kn - 1)];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                extendMatrix[i + km - 1][j + kn - 1] = matrix[i][j];
        }

        return convnValid(extendMatrix, kernel);
    }

    public static double[][] convnValid(final double[][] matrix,
                                        double[][] kernel) {

        int m = matrix.length;
        int n = matrix[0].length;
        final int km = kernel.length;
        final int kn = kernel[0].length;

        int kns = n - kn + 1;

        final int kms = m - km + 1;

        final double[][] outMatrix = new double[kms][kns];

        for (int i = 0; i < kms; i++) {
            for (int j = 0; j < kns; j++) {
                double sum = 0.0;
                for (int ki = 0; ki < km; ki++) {
                    for (int kj = 0; kj < kn; kj++)
                        sum += matrix[i + ki][j + kj] * kernel[ki][kj];
                }
                outMatrix[i][j] = sum;

            }
        }
        return outMatrix;

    }


    public double[][] randomizeFilter(int size) {

        double[][] weights = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                weights[i][j] = Math.random();
            }
        }

        return weights;
    }

    public double[][] getConvoluteShape(double[][] shape, double[][] filter, ConvType type) {

        if(shape.length < filter.length) {
            double[][] temp = shape;
            shape = filter;
            filter = temp;
        }

        double[][] newShape = null;
        if(type.equals(ConvType.full)) {
            newShape = convnFull(shape, filter);
        }
        else if(type.equals(ConvType.valid)) {
            newShape = convnValid(shape, filter);


        }
        return newShape;
    }
}
