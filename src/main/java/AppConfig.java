import helpers.ConvolutionHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ConvolutionHelper convolutionHelper() {
        return new ConvolutionHelper();
    }

}
