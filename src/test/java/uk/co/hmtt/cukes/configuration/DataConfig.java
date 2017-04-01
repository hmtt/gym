package uk.co.hmtt.cukes.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.hmtt.gym.config.app.DevDataConfig;
import uk.co.hmtt.gym.config.app.ProdDataConfig;
import uk.co.hmtt.gym.config.app.TestDataConfig;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import({DevDataConfig.class, ProdDataConfig.class, TestDataConfig.class})
@EnableTransactionManagement
public class DataConfig {

    @Value("${gym.db.driver}")
    private String driver;

    @Value("${gym.db.username}")
    private String username;

    @Value("${gym.db.password}")
    private String password;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUrl("jdbc:h2:" + System.getProperty("user.dir") + "/target/db/gym;AUTO_SERVER=TRUE");
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder =
                new LocalSessionFactoryBuilder(dataSource());
        builder
                .addProperties(getHibernateProperties());
        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return prop;
    }

    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }


}
