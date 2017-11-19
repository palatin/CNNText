package helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHelperTest {


    @Test
    public void subArray() {

        double[][] array = {{5,8,7}, {4,2,5}, {3,10,9}};

        double[][] result = {{4,2,5}, {3,10,9}};

        assertArrayEquals(result, ArrayHelper.subArray(array, 1,2,0,2));

    }

}