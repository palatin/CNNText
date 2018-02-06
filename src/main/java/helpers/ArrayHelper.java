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

    public static double[][] matrixMultiplication(double[][] firstMatrix, double[][] secondMatrix) {

        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];


        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[0].length; j++) {
                for (int k = 0; k < firstMatrix[0].length; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return result;
    }

    public static double[][] upsample(double[][] errors,int n) {


        double[][] ones = new double[n / errors.length][n / errors[0].length];
        for (int i = 0; i < ones.length; i++) {
            for (int j = 0; j < ones[0].length; j++) {
                ones[i][j] = 1;
            }
        }

        return ArrayHelper.kroneckerProduct(errors, ones);
    }



    public static double[][] rotateSquareMatrixBy180Degree(double[][] matrix) {

        int size = matrix.length;
        for (int i = 0; i <  matrix.length / 2; i++) {
            for (int j = 0; j < size; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[size - i- 1][size - j - 1];
                matrix[size - i- 1][size - j - 1] = temp;
            }
        }

        if (size % 2 != 0)
            for (int i = 0; i< size/2; i++ ) {
                double temp = matrix[size/2][i];
                matrix[size/2][i] = matrix[size/2][size - i - 1];
                matrix[size/2][size - i - 1] = temp;
            }

        return matrix;
    }

    public static double[][] kroneckerProduct(double[][] matrix1, double[][] matrix2) {
        double[][] newMatrix = new double[matrix1.length*matrix2.length][matrix1[0].length * matrix2[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                for (int k = 0; k < matrix2.length; k++) {
                    for (int l = 0; l < matrix2[0].length; l++) {
                        newMatrix[i*matrix2.length + k][j*matrix2[0].length + l] = matrix1[i][j] * matrix2[k][l];
                    }
                }
            }
        }

        return newMatrix;
    }

    public static double mean(double[] arr) {


        return sum(arr) / arr.length;
    }

    public static double sum(double[] arr) {
        double sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        return sum;
    }


}
