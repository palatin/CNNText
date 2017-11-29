package application;

import helpers.ConvolutionHelper;
import helpers.SubSamplingHelper;
import network.CNNNetwork;
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
    CNNNetwork.CNNNetworkBuilder cnnNetworkBuilder(ConvolutionHelper convolutionHelper, SubSamplingHelper subSamplingHelper) {

        return CNNNetwork.builder(convolutionHelper, subSamplingHelper);
    }

}
