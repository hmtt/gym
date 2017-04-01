package uk.co.hmtt.gym.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import uk.co.hmtt.gym.app.annotations.AppTest;
import uk.co.hmtt.gym.app.annotations.Production;
import uk.co.hmtt.gym.app.schedule.SpringSchedular;
import uk.co.hmtt.gym.config.app.DataConfig;

@Configuration
@ComponentScan({"uk.co.hmtt.gym.app"})
@EnableScheduling
@Import({DataConfig.class})
//@PropertySource(value = {"classpath:/environment-prod.properties"})
public class SpringAppConfig {

    final Logger logger = LoggerFactory.getLogger(SpringAppConfig.class);

    @Bean
    @Production
    public SpringSchedular task() {
        return new SpringSchedular();
    }

    @Bean
    @Production
    public static PropertySourcesPlaceholderConfigurer propertyConfigInProd() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("environment-prod.properties"));
        return configurer;
    }

    @Bean
    @AppTest
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("environment-dev.properties"));
        return configurer;
    }

}
