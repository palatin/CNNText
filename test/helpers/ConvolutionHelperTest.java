package helpers;

import org.junit.Assert;
import org.junit.Test;

public class ConvolutionHelperTest {



    @Test
    public void convolutionTestFull() {

        double[][] shape = {{8,1,6}, {3,5,7}, {4,9,2}};

        double[][] core = {{1,1}, {1,1}};


        Assert.assertArrayEquals(new double[][]{{8,9,7,6}, {11,17,19,13}, {7,21,23,9}, {4,13,11,2}},
                new ConvolutionHelper().getConvoluteShape(shape, core, ConvolutionHelper.ConvType.full));
    }

    @Test
    public void convolutionTestValid() {

        double[][] shape = {{8,1,6}, {3,5,7}, {4,9,2}};

        double[][] core = {{1,1}, {1,1}};

        Assert.assertArrayEquals(new double[][]{{17,19}, {21,23}},
                new ConvolutionHelper().getConvoluteShape(shape, core, ConvolutionHelper.ConvType.valid));
    }


}