package uk.co.hmtt.gym.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.config.app.DataConfig;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = {DataConfig.class})
@PropertySource("classpath:/environment-dev.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public abstract class RepositoryTestConfig {

    public static final String CLASS_NAME = "spin";
    public static final String CLASS_DATE = "12/14/2019";

    @Autowired
    protected UserDao userDao;


    @Autowired
    protected ActivityDao activityDao;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    protected User getOnlyUser() {
        final Set<User> users = userDao.findUsers();
        assertThat(users.size(), is(equalTo(1)));
        return users.stream().findFirst().get();
    }

}
