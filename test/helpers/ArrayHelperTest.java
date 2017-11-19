package helpers;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ArrayHelperTest {



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