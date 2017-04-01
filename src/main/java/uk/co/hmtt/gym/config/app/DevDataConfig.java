package uk.co.hmtt.gym.config.app;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import uk.co.hmtt.gym.app.annotations.AppTest;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@AppTest
public class DevDataConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);

    private String siteName = "booker";

    @Bean
    public boolean cleanDb() {
        try {
            FileUtils.forceDelete(new File(System.getProperty("user.dir") + "/target/db"));
        } catch (IOException e) {
            LOGGER.error("Could not delete DB files", e);
        }
        return true;
    }

    @Bean
    @DependsOn("cleanDb")
    public DataSource dataSource() {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
        basicDataSource.setUrl("jdbc:h2:" + System.getProperty("user.dir") + "/target/db/gym;AUTO_SERVER=TRUE");
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("");
        return basicDataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(new Populator());
        return dataSourceInitializer;
    }

    @Bean
    public SpringLiquibase liquibase() {
        dataSourceInitializer();
        final SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource());
        springLiquibase.setChangeLog("classpath:changeset/changeset_dev.xml");
        springLiquibase.setContexts("test, production");
        return springLiquibase;
    }

    private class Populator implements DatabasePopulator {
        @Override
        public void populate(Connection connection) throws SQLException {
            Statement statement = connection.createStatement();
            try {
                statement.execute("CREATE SCHEMA " + siteName);
            } catch (JdbcSQLException e) {
                LOGGER.error("Could not create schema", e);
            } finally {
                statement.close();
            }
        }
    }

}
