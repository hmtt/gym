package uk.co.hmtt.gym.repository;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.service.GymUserActivityServiceTest;
import uk.co.hmtt.gym.repository.impl.GymActivityDao;
import uk.co.hmtt.gym.repository.impl.GymUserDao;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;




@ContextConfiguration(classes = {GymActivityDao.class, GymUserDao.class, GymUserActivityServiceTest.DataLoad.class})
public class ActivityDaoTest extends RepositoryTestConfig {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void shouldAddAnActivityIfOneDoesNotAlreadyExist() {

        activityDao.addActivity(CLASS_NAME, CLASS_DATE);

        final List<Activity> allActivities = sessionFactory.getCurrentSession().createQuery("from Activity order by className").list();
        final boolean activityWasCommitted = allActivities.stream().anyMatch(activity -> CLASS_NAME.equals(activity.getClassName()) && CLASS_DATE.equals(activity.getClassDate()));
        assertThat(activityWasCommitted, is(true));

    }

    @Test
    public void shouldUpdateAnActivityThatAlreadyExists() {

        activityDao.addActivity(CLASS_NAME, CLASS_DATE);

        final List<Activity> originalRecord = sessionFactory.getCurrentSession().createQuery("from Activity order by className").list();
        assertThat(originalRecord.size(), is(equalTo(1)));

        final Date originalLastChecked = originalRecord.get(0).getLastChecked();

        activityDao.addActivity(CLASS_NAME, CLASS_DATE);

        final List<Activity> updatedRecord = sessionFactory.getCurrentSession().createQuery("from Activity order by className").list();
        assertThat(updatedRecord.size(), is(equalTo(1)));
        assertThat(updatedRecord.get(0).getLastChecked(), is(greaterThan(originalLastChecked)));

    }

    @Test
    public void shouldRetrieveAnActivityByClassNameAndClassDateIfTheActivityExists() {
        activityDao.addActivity(CLASS_NAME, CLASS_DATE);
        final Activity activity = activityDao.getActivity(CLASS_NAME, CLASS_DATE);
        assertThat(activity, is(notNullValue()));
        assertThat(activity.getClassName(), is(equalTo(CLASS_NAME)));
        assertThat(activity.getClassDate(), is(equalTo(CLASS_DATE)));
    }

    @Test
    public void shouldRetrieveNullIfTheActivityCanNotBeFoundByClassNameAndClassDate() {
        final Activity activity = activityDao.getActivity(CLASS_NAME, CLASS_DATE);
        assertThat(activity, is(nullValue()));
    }

    @Test
    public void shouldRetrieveAnActivityByIdIfTheActivityExists() {
        final int id = activityDao.addActivity(CLASS_NAME, CLASS_DATE);
        final Activity activity = activityDao.getActivity(id);
        assertThat(activity, is(notNullValue()));
        assertThat(activity.getClassName(), is(equalTo(CLASS_NAME)));
        assertThat(activity.getClassDate(), is(equalTo(CLASS_DATE)));
    }

    @Test
    public void shouldRetrieveNullIfTheActivityCanNotBeFoundById() {
        final Activity activity = activityDao.getActivity(1);
        assertThat(activity, is(nullValue()));
    }

    @Test
    public void shouldRetrieveAllActivities() {
        activityDao.addActivity("ACTIVITY_1", "12/14/2017");
        activityDao.addActivity("ACTIVITY_2", "12/14/2018");
        activityDao.addActivity("ACTIVITY_3", "12/14/2019");

        final List<Activity> allActivities = activityDao.getAllActivities();

        assertThat(allActivities.size(), is(equalTo(3)));
    }

    @Test
    public void shouldRetrieveAllTimesForASpecifiedActivity() {
        activityDao.addActivity(CLASS_NAME, "12/14/2017");
        activityDao.addActivity(CLASS_NAME, "12/14/2018");

        final List<Activity> allActivities = activityDao.getTimesForActivity(CLASS_NAME);

        assertThat(allActivities.size(), is(equalTo(2)));

        assertThat(allActivities.get(0).getClassDate(), is(equalTo("12/14/2017")));
        assertThat(allActivities.get(1).getClassDate(), is(equalTo("12/14/2018")));

    }

    @Before
    public void init() {
        sessionFactory.getCurrentSession().createQuery("delete from UserActivity").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("delete from Activity").executeUpdate();
        assertNoActivitiesPresent();
    }

    private void assertNoActivitiesPresent() {
        final List<Activity> allActivities = sessionFactory.getCurrentSession().createQuery("from Activity").list();
        assertThat(allActivities, is(empty()));
    }

    @Configuration
    public static class DataLoad {

        @Autowired
        private DataSource dataSource;

        @Bean
        public SpringLiquibase liquibase() {
            final SpringLiquibase springLiquibase = new SpringLiquibase();
            springLiquibase.setDataSource(dataSource);
            springLiquibase.setContexts("test");
            return springLiquibase;
        }

    }

}