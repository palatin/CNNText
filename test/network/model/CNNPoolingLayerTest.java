package network.model;

import exception.CNNLayerException;
import helpers.SubSamplingHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CNNPoolingLayerTest {

    @Mock
    private SubSamplingHelper subSamplingHelper;

    @Test
    public void processShapes() throws CNNLayerException {

        CNNPoolingLayer layer = new CNNPoolingLayer(subSamplingHelper);

        ArrayList<double[][]> shapes = new ArrayList<>();


        shapes.add(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}});
        shapes.add(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}});

        when(subSamplingHelper.subsampling(eq(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}}), eq(0),eq(0),eq(2))).thenReturn(6.0);
        when(subSamplingHelper.subsampling(eq(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}}), eq(0),eq(2),eq(2))).thenReturn(8.0);
        when(subSamplingHelper.subsampling(eq(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}}), eq(2),eq(0),eq(2))).thenReturn(3.0);
        when(subSamplingHelper.subsampling(eq(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}}), eq(2),eq(2),eq(2))).thenReturn(4.0);
        ArrayList<double[][]> shapesExp = new ArrayList<>();

        shapesExp.add(new double[][] {{6,8}, {3,4}});
        shapesExp.add(new double[][] {{6,8}, {3,4}});


        assertArrayEquals(shapesExp.toArray(), layer.processShapes(shapes).toArray());

    }

}