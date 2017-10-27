package network;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConvolutionHelperTest {


    @Test
    public void convolution() {

        int[][] shape = {{5,8,7}, {4,2,5}, {3,8,9}};

        int[][] core = {{5,7,2}, {5,3,6}, {3,3,-1}};

        int res = 175;

        Assert.assertEquals(res, ConvolutionHelper.convolution(shape,core));
    }

}