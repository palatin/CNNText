package network.cnnlayer;

import helpers.ConvolutionHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CNNConvolutionLayerTest {

    @Mock
    private ConvolutionHelper convolutionHelper;


    @Test
    public void processShapes() throws Exception {

        ArrayList<double[][]> convCores = new ArrayList<double[][]>();

        convCores.add(new double[][] {{5,7,2}, {5,3,6}, {3,3,-1}});
        convCores.add(new double[][] {{5,7,4}, {5,3,6}, {3,3,-1}});

        CNNLayer layer = new CNNConvolutionLayer(convCores, new int[][] {{0, 1}, {0}}, convolutionHelper);

        ArrayList<double[][]> shapes = new ArrayList<double[][]>();
        shapes.add(new double[][]{{5,8,7}, {4,2,5}, {3,8,9}});
        shapes.add(new double[][]{{5,2,3}, {4,2,5}, {3,8,9}});

        ArrayList<double[][]> expectedShapes = new ArrayList<double[][]>();

        expectedShapes.add(new double[][]{{175.0}});
        expectedShapes.add(new double[][]{{189.0}});
        expectedShapes.add(new double[][]{{125.0}});

        when(convolutionHelper.convolution(eq(shapes.get(0)), eq(convCores.get(0)), anyInt(), anyInt())).thenReturn(175.0);
        when(convolutionHelper.convolution(eq(shapes.get(0)), eq(convCores.get(1)), anyInt(), anyInt())).thenReturn(189.0);
        when(convolutionHelper.convolution(eq(shapes.get(1)), eq(convCores.get(0)), anyInt(), anyInt())).thenReturn(125.0);

        assertArrayEquals(expectedShapes.toArray(), layer.processShapes(shapes).toArray());
    }

}