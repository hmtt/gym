package uk.co.hmtt.gym.app.service;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.model.UserActivity;
import uk.co.hmtt.gym.app.service.impl.GymUserActivityService;
import uk.co.hmtt.gym.repository.impl.GymUserActivityDao;
import uk.co.hmtt.gym.repository.RepositoryTestConfig;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ContextConfiguration(classes = {GymUserActivityService.class, GymUserActivityDao.class, GymUserActivityServiceTest.DataLoad.class})
public class GymUserActivityServiceTest extends RepositoryTestConfig {

    @Autowired
    private GymUserActivityService gymUserActivityService;

    @Test
    @Ignore
    public void shouldRetrieveAllScheduledActivitiesForAllUsers() {
        final Map<User, Set<UserActivity>> bookingRequests = gymUserActivityService.getBookingRequests();
        assertThat(bookingRequests.size(), is(equalTo(1)));
        assertThat(containsExclusions(bookingRequests), is(true));
    }

    private boolean containsExclusions(Map<User, Set<UserActivity>> bookingRequests) {
        boolean foundExclusions = false;
        for (User user : bookingRequests.keySet()) {
            final Iterator<UserActivity> iterator = bookingRequests.get(user).iterator();
            while (iterator.hasNext()) {
                final UserActivity userActivity = iterator.next();
                if (!userActivity.getExclusions().isEmpty()) {
                    foundExclusions = true;
                    break;
                }
            }
        }
        return foundExclusions;
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
