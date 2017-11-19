package network;

import exception.EmptyShapeException;

public class SubSamplingHelper {


    /**
     * @param shape - shape matrix
     * @return max element from shape matrix
     * @throws EmptyShapeException if shape matrix is empty
     */
    public double subsampling(double[][] shape) {


        double max = shape[0][0];

        int size = shape[0].length;

        for (int i = 0; i < size; i++) {
            for (double el: shape[i]) {
                if(max < el)
                    max = el;
            }
        }

        return max;
    }

    /**
     * @param shape - shape matrix
     * @return max element from shape matrix
     * @throws EmptyShapeException if shape matrix is empty
     */
    public double subsampling(double[][] shape, int startRow, int startColumn, int poolingWindow) {


        double max = shape[startRow][startColumn];


        for (int i = startRow; i < poolingWindow + startRow; i++) {
            for (int j = startColumn; j < poolingWindow + startColumn; j++) {
                if(max < shape[i][j])
                    max = shape[i][j];
            }
        }

        return max;
    }


}
