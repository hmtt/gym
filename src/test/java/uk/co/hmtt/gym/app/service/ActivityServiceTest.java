package uk.co.hmtt.gym.app.service;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.gym.app.service.impl.GymActivityService;
import uk.co.hmtt.gym.repository.impl.GymActivityDao;
import uk.co.hmtt.gym.repository.RepositoryTestConfig;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ContextConfiguration(classes = {GymActivityService.class, GymActivityDao.class, ActivityServiceTest.DataLoad.class})
public class ActivityServiceTest extends RepositoryTestConfig {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String siteName = "booker";

    @Test
    @Ignore
    public void shouldAddAnActivity() {
        final Integer countBeforeAddition = getRowCount();
        activityService.addActivity("The Peak - Velocity Spin", "Velocity Blast Fri 07:15");
        final Integer countAfterAddition = getRowCount();
        assertThat(countAfterAddition, is(equalTo(countBeforeAddition + 1)));
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
            springLiquibase.setChangeLog("classpath:changeset/changeset_activity_single.xml");
            springLiquibase.setContexts("test, production");
            return springLiquibase;
        }

    }

}
