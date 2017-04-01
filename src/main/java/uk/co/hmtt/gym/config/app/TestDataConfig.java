package uk.co.hmtt.gym.config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import uk.co.hmtt.gym.app.annotations.UnitTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@UnitTest
@PropertySource("classpath:/environment-prod.properties")
public class TestDataConfig {

    private String siteName = "booker";

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(new Populator());
        return dataSourceInitializer;
    }

    private class Populator implements DatabasePopulator {
        @Override
        public void populate(Connection connection) throws SQLException {
            Statement statement = connection.createStatement();
            try {
                statement.execute("CREATE SCHEMA " + siteName);
            } finally {
                statement.close();
            }
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
