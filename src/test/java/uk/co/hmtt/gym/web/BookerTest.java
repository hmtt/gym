package uk.co.hmtt.gym.web;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.hmtt.gym.app.service.impl.GymUserActivityService;
import uk.co.hmtt.gym.config.app.DataConfig;
import uk.co.hmtt.gym.provider.thepeak.Booker;
import uk.co.hmtt.gym.repository.impl.GymUserActivityDao;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class, Booker.class, GymUserActivityService.class, GymUserActivityDao.class, BookerTest.DataLoad.class})
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Ignore
public class BookerTest {

    @Autowired
    private Booker booker;

    @Test
    public void runBooker() {
        booker.book();
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
