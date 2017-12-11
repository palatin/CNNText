package network.cnnlayer;

import helpers.ConvolutionHelper;
import network.cnnlayer.model.FeatureMap;
import network.perceptronlayer.Neuron;
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



        ArrayList<FeatureMap> featureMapsInput = new ArrayList<>(2);
        ArrayList<double[][]> filters = new ArrayList<>(3);

        filters.add(new double[][] {{5,7,2}, {5,3,6}, {3,3,-1}});
        filters.add(new double[][] {{5,7,4}, {5,3,6}, {3,3,-1}});

        //CNNLayer layer = new CNNConvolutionLayer(filters, new int[][] {{0, 1}, {0}}, convolutionHelper, 0.1);



        //when(convolutionHelper.convolution(eq(new double[][]{{5,8,7}, {4,2,5}, {3,8,9}}), eq(new double[][] {{5,7,2}, {5,3,6}, {3,3,-1}}), anyInt(), anyInt())).thenReturn(175.0);
        //when(convolutionHelper.convolution(eq(new double[][]{{5,8,7}, {4,2,5}, {3,8,9}}), eq(new double[][] {{5,7,4}, {5,3,6}, {3,3,-1}}), anyInt(), anyInt())).thenReturn(189.0);
        //when(convolutionHelper.convolution(eq(new double[][]{{5,2,3}, {4,2,5}, {3,8,9}}), eq(new double[][] {{5,7,2}, {5,3,6}, {3,3,-1}}), anyInt(), anyInt())).thenReturn(125.0);
        FeatureMap featureMap = new FeatureMap();
        featureMap.setOutputs(new double[][]{{5,8,7}, {4,2,5}, {3,8,9}});
        FeatureMap featureMap2 = new FeatureMap();
        featureMap2.setOutputs(new double[][]{{5,2,3}, {4,2,5}, {3,8,9}});

        featureMapsInput.add(featureMap);
        featureMapsInput.add(featureMap2);

        double[][] exp = new double[][] {{175}, {189}, {125}};
        //ArrayList<FeatureMap> actual = layer.process(featureMapsInput);
        //for (int i = 0; i < actual.size(); i++) {
        //    assertEquals(exp[i][0], actual.get(i).getOutputs()[0][0], 0);
        //}


    }

}