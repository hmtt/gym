package uk.co.hmtt.gym.repository;

import org.hamcrest.Matcher;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.gym.app.model.*;
import uk.co.hmtt.gym.app.service.GymUserActivityServiceTest;
import uk.co.hmtt.gym.repository.impl.GymActivityDao;
import uk.co.hmtt.gym.repository.impl.GymUserActivityDao;
import uk.co.hmtt.gym.repository.impl.GymUserDao;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {GymUserActivityDao.class, GymUserDao.class, GymActivityDao.class, GymUserActivityServiceTest.DataLoad.class})
public class UseActivityDaoTest extends RepositoryTestConfig {

    public static final String USER_EMAIL = "abc@xyz.com";
    public static final String PASS_CODE = "1234";

    @Autowired
    private UserActivityDao userActivityDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void shouldRetrieveAUserIfUserExistsForSpecifiedEmail() {

        final User aUser = addUser();
        final int activityId = activityDao.addActivity(CLASS_NAME, CLASS_DATE);
        final UserActivity userActivity = configureUserActivityWithNoExclusions(aUser, activityId);

        addAnExclusion(userActivity);

        assertThatExclusion(is(not(empty())), userActivity);

    }

    private UserActivity configureUserActivityWithNoExclusions(User aUser, int activityId) {
        final UserActivity userActivity = createUserActivity(aUser, activityId);
        userActivityDao.addScheduledBookingRequest(userActivity);
        assertThatExclusion(is(empty()), userActivity);
        return userActivity;
    }

    private void assertThatExclusion(Matcher<Collection<? extends Exclusion>> matcher, final UserActivity userActivity) {
        final List<Exclusion> userBookingWithExclusion = sessionFactory.getCurrentSession().createQuery("from Exclusion where userActivity_userId = :userActivity_userId and userActivity_activityId = :userActivity_activityId").setInteger("userActivity_userId", userActivity.getUser().getId()).setInteger("userActivity_activityId", userActivity.getActivity().getId()).list();
        assertThat(userBookingWithExclusion, matcher);
    }

    private UserActivity createUserActivity(User aUser, int activityId) {
        final UserActivity userActivity = new UserActivity();
        userActivity.setUser(aUser);
        userActivity.setActivity(activityDao.getActivity(activityId));
        return userActivity;
    }

    private User addUser() {
        final User aUser = new User(USER_EMAIL, PASS_CODE);
        userDao.updateUser(aUser);
        return aUser;
    }

    private void addAnExclusion(UserActivity userActivity) {
        final Exclusion exclusion = new Exclusion();
        exclusion.setExclusionDate(new Date());
        final ExclusionId exclusionId = new ExclusionId();
        exclusionId.setUserActivity(userActivity);
        exclusion.setPk(exclusionId);
        userActivityDao.addExclusion(exclusion);
    }

    @Before
    public void init() {
        sessionFactory.getCurrentSession().createQuery("delete from UserActivity").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("delete from User").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("delete from Activity").executeUpdate();
        assertNoUsersPresent();
    }

    private void assertNoUsersPresent() {
        final List<Activity> allActivities = sessionFactory.getCurrentSession().createQuery("from UserActivity").list();
        assertThat(allActivities, is(empty()));
    }

}