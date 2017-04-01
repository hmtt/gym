package uk.co.hmtt.gym.app.service;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.impl.GymUserService;
import uk.co.hmtt.gym.repository.impl.GymUserDao;
import uk.co.hmtt.gym.repository.RepositoryTestConfig;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@ContextConfiguration(classes = {GymUserService.class, GymUserDao.class, UserServiceTest.DataLoad.class})
public class UserServiceTest extends RepositoryTestConfig {

    @Autowired
    private UserService userService;

    @Test
    @Ignore
    public void shouldRetrieveAUserGivenAnEmailAddress() {
        final User user = userService.getUser("admin@hmtt.co.uk");
        assertThat(user.getEmail(), is(equalTo("admin@hmtt.co.uk")));
    }

    @Configuration
    public static class DataLoad {

        @Autowired
        private DataSource dataSource;

        @Bean
        public SpringLiquibase liquibase() {
            final SpringLiquibase springLiquibase = new SpringLiquibase();
            springLiquibase.setDataSource(dataSource);
            springLiquibase.setChangeLog("classpath:/changeset/changeset_user_add.xml");
            springLiquibase.setContexts("test, production");
            return springLiquibase;
        }

    }

}
