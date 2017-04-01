package uk.co.hmtt.cukes.configuration;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan(basePackages = {"uk.co.hmtt.cukes.core"})
@PropertySource({"classpath:/environment-dev.properties"})
@Import(value = {DataConfig.class})
@ActiveProfiles(value = {"dev"})
@EnableAspectJAutoProxy
public class CukesConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
