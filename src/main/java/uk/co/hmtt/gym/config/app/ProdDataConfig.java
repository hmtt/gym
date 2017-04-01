package uk.co.hmtt.gym.config.app;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.hmtt.gym.app.annotations.Production;

import javax.sql.DataSource;

@Configuration
@Production
public class ProdDataConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        String username = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String databaseName = System.getenv("OPENSHIFT_APP_NAME");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(1800000);
        dataSource.setNumTestsPerEvictionRun(3);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        dataSource.setValidationQuery("SELECT version()");


        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        final SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource());
        springLiquibase.setChangeLog("classpath:changeset/changeset_production.xml");
        springLiquibase.setContexts("test, production");
        return springLiquibase;
    }

}
