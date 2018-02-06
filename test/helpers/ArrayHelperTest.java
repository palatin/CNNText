package helpers;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ArrayHelperTest {
    @Test
    public void flatten() throws Exception {
    }

    @Test
    public void flatten1() throws Exception {
    }

    @Test
    public void kroneckerProduct() throws Exception {
        assertArrayEquals(new double[][]{{0,5,0,10}, {6,7,12,14}, {0,15,0,20}, {18,21,24,28}},
                ArrayHelper.kroneckerProduct(new double[][]{{1,2}, {3,4}}, new double[][]{{0,5}, {6,7}}));
    }

    @Test
    public void dotProduct() throws Exception {
    }

    @Test
    public void rotateSquareMatrixBy180Degree() throws Exception {

        assertArrayEquals(new double[][]{{9,8,7}, {6,5,4}, {3,2,1}} ,ArrayHelper.rotateSquareMatrixBy180Degree(new double[][]{{1,2,3}, {4,5,6}, {7,8,9}}));

    }


    @Test
    public void flattenTwoDim() throws Exception {

        double[][] array = {{5,8,7}, {4,2,5}, {3,10,9}};

        double[] exp = {5,8,7,4,2,5,3,10,9};

        assertArrayEquals(exp, ArrayHelper.flatten(array), 0);
    }

    @Test
    public void flattenArrayOfTwoDim() throws Exception {

        ArrayList<double[][]> convCores = new ArrayList<double[][]>();

        convCores.add(new double[][] {{5,7,2}, {5,3,6}, {3,3,-1}});
        convCores.add(new double[][] {{5,7,6}, {5,3,6}, {3,3,-1}});


        double[] exp = {5,7,2,5,3,6,3,3,-1, 5,7,6,5,3,6,3,3,-1};

        assertArrayEquals(exp, ArrayHelper.flatten(convCores), 0);
    }


    @Test
    public void subArray() {

        double[][] array = {{5,8,7}, {4,2,5}, {3,10,9}};

        double[][] result = {{4,2,5}, {3,10,9}};

        assertArrayEquals(result, ArrayHelper.subArray(array, 1,2,0,2));

    }

}