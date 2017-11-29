package network;

import helpers.ConvolutionHelper;
import helpers.SubSamplingHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CNNNetworkTest {



    @Mock
    ConvolutionHelper convolutionHelper;

    @Mock
    SubSamplingHelper subSamplingHelper;

    @Test
    public void recognize() throws Exception {


        CNNNetwork.builder(convolutionHelper, subSamplingHelper).poolingLayer()
                .build()
                .recognize(new double[][] {{1,0,2,3}, {4,6,6,8}, {3,1,1,0}, {1,2,2,4}});

    }

}