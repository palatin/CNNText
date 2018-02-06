package helpers;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubSamplingHelperTest {


    @Test
    public void subsampling() {

        double[][] shape = {{5,8,7}, {4,2,5}, {3,10,9}};

        Assert.assertEquals(10, new SubSamplingHelper().subsampling(shape), 0);

    }

    @Test
    public void subsamplingWithRange() {

        double[][] shape = {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}};

        assertEquals(3, new SubSamplingHelper().subsampling(shape, 2, 0 , 2), 0);

    }

}