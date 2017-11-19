package network;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConvolutionHelperTest {



    @Test
    public void convolution() {

        double[][] shape = {{5,8,7}, {4,2,5}, {3,8,9}};

        double[][] core = {{5,7,2}, {5,3,6}, {3,3,-1}};

        int res = 175;

        Assert.assertEquals(res, new ConvolutionHelper().convolution(shape,core), 0);
    }

    @Test
    public void convolutionWithRange() {


        double[][] shape = {{5,8,7}, {4,2,5}, {3,8,9}};

        double[][] core = {{5,3}, {3,-1}};

        int res = 27;

        Assert.assertEquals(res, new ConvolutionHelper().convolution(shape,core, 1, 0), 0);

    }
}