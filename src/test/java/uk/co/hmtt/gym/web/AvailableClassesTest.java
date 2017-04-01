package uk.co.hmtt.gym.web;


import liquibase.integration.spring.SpringLiquibase;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.gym.app.service.impl.GymActivityService;
import uk.co.hmtt.gym.app.service.impl.GymUserService;
import uk.co.hmtt.gym.config.app.DataConfig;
import uk.co.hmtt.gym.provider.thepeak.AvailableClasses;
import uk.co.hmtt.gym.provider.thepeak.pageobjects.HomePage;
import uk.co.hmtt.gym.provider.thepeak.process.ActivityPageAction;
import uk.co.hmtt.gym.provider.thepeak.process.ActivityTimeAction;
import uk.co.hmtt.gym.provider.thepeak.process.HomePageSelection;
import uk.co.hmtt.gym.provider.thepeak.process.Logon;
import uk.co.hmtt.gym.repository.impl.GymActivityDao;
import uk.co.hmtt.gym.repository.impl.GymUserActivityDao;
import uk.co.hmtt.gym.repository.impl.GymUserDao;
import uk.co.hmtt.gym.repository.RepositoryTestConfig;

import javax.sql.DataSource;

@Ignore
@PropertySource(value = "environment-dev.properties")
@ContextConfiguration(classes = {DataConfig.class, AvailableClasses.class, GymUserService.class, GymActivityService.class, GymActivityDao.class, GymUserActivityDao.class, GymUserDao.class, ActivityTimeAction.class, ActivityPageAction.class, Logon.class, HomePage.class, HomePageSelection.class, AvailableClassesTest.DataLoad.class})
public class AvailableClassesTest extends RepositoryTestConfig {

    private String siteName = "booker";

    private final static Logger LOGGER = LoggerFactory.getLogger(AvailableClassesTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AvailableClasses availableClasses;

    @Test
    public void shouldDetermineAllAvailableClassesAndUpdateTheActivitiesTableToReflectClassesOffered() {
        LOGGER.debug("number of activities before {}", getRowCount());
        availableClasses.find();
        LOGGER.debug("number of activities after {}", getRowCount());
    }

    private Integer getRowCount() {
        RowCountCallbackHandler countCallbackHandler = new RowCountCallbackHandler();
        jdbcTemplate.query("select * from " + siteName + ".Activity", countCallbackHandler);
        return countCallbackHandler.getRowCount();
    }

    @Configuration
    public static class DataLoad {

        @Autowired
        private DataSource dataSource;

        @Bean
        public SpringLiquibase liquibase() {
            final SpringLiquibase springLiquibase = new SpringLiquibase();
            springLiquibase.setDataSource(dataSource);
            springLiquibase.setChangeLog("classpath:changeset/changeset_bookings_add.xml");
            springLiquibase.setContexts("test, production");
            return springLiquibase;
        }

    }

}
