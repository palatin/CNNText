package application;

import exception.InvalidShapeException;
import helpers.ConvolutionHelper;
import helpers.ImageLoader;
import helpers.SubSamplingHelper;
import interfaces.LossFunction;
import network.CNNNetwork;
import network.CrossEntropyLossFunction;
import network.Perceptron;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ConvolutionHelper convolutionHelper() {
        return new ConvolutionHelper();
    }

    @Bean
    SubSamplingHelper subSamplingHelper() {
        return new SubSamplingHelper();
    }

    @Bean
    LossFunction lossFunction() {

        return new CrossEntropyLossFunction();
    }

    @Bean
    CNNNetwork.CNNNetworkBuilder cnnNetworkBuilder(ConvolutionHelper convolutionHelper, SubSamplingHelper subSamplingHelper) {

        return CNNNetwork.builder(convolutionHelper, subSamplingHelper, NetworkConfig.LEARNING_RATE, NetworkConfig.CLASSES, lossFunction());
    }

    @Bean
    ImageLoader imageLoader() {

        return new ImageLoader();
    }

    @Bean
    CNNNetwork cnnNetwork(CNNNetwork.CNNNetworkBuilder builder) throws InvalidShapeException {

        Perceptron perceptron = new Perceptron().builder(NetworkConfig.LEARNING_RATE)
                .perceptronLayer(100, 2, Perceptron.LayerType.RELU)
                .perceptronLayer(11,0, Perceptron.LayerType.SoftMax)
                .build();

        return builder.convolutionLayer(6, NetworkConfig.FILTER_SIZE)
                .poolingLayer()
                .convolutionLayer(16, NetworkConfig.FILTER_SIZE)
                .poolingLayer()
                .convolutionLayer(120, NetworkConfig.FILTER_SIZE)
                .build(perceptron);
    }



}
