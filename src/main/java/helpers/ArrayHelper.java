package helpers;

import java.util.ArrayList;

public class ArrayHelper {


    /**get sub array from two-d double array
     * @param array - current array
     * @param startRow
     * @param endRow
     * @param startColumn
     * @param endColumn
     * @return sub array
     */
    public static double[][] subArray(double[][] array, int startRow, int endRow, int startColumn, int endColumn) {

        double[][] res = new double[endRow - startRow + 1][endColumn - startColumn + 1];

        for (int i = 0; i <= endRow - startRow; i++) {
            for (int j = 0; j <= endColumn - startColumn; j++) {
                res[i][j] = array[i + startRow][j + startColumn];
            }
        }

        return res;
    }

    /**transform arraylist of two-dim. doubles to one-dim double array
     * @param values
     * @return
     */
    public static double[] flatten(ArrayList<double[][]> values) {

        double[] arr = new double[values.size() * values.get(0).length * values.get(0)[0].length];

        for (int i = 0; i < values.size(); i++) {

            double[] temp = flatten(values.get(i));

            for (int j = 0; j < temp.length; j++) {
                arr[i*temp.length + j] = temp[j];
            }

        }

        return arr;
    }

    /**transform two-dim. double array to one-dim double array
     * @param values
     * @return
     */
    public static double[] flatten(double[][] values) {

        double[] arr = new double[values.length * values[0].length];

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                arr[i*values[0].length + j] = values[i][j];
            }
        }

        return arr;
    }

    public static double dotProduct(double[] x, double[] y) {

        double sum = 0;

        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }

        return sum;
    }


}
