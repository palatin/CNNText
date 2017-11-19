package helpers;

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
}
