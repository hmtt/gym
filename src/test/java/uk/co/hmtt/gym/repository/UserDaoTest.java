package uk.co.hmtt.gym.repository;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.GymUserActivityServiceTest;
import uk.co.hmtt.gym.repository.impl.GymActivityDao;
import uk.co.hmtt.gym.repository.impl.GymUserDao;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {GymUserDao.class, GymActivityDao.class, GymUserActivityServiceTest.DataLoad.class})
public class UserDaoTest extends RepositoryTestConfig {

    public static final String USER_EMAIL = "abc@xyz.com";
    public static final String PASS_CODE = "1234";

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void shouldRetrieveAUserIfUserExistsForSpecifiedEmail() {

        addUser(USER_EMAIL, PASS_CODE);

        final User user = userDao.findUser(USER_EMAIL);

        assertThat(user, is(notNullValue()));
        assertThat(user.getEmail(), is(equalTo(USER_EMAIL)));
    }

    @Test
    public void shouldReturnNullIfUserDoesNotExistsForSpecifiedEmail() {
        assertThat(userDao.findUser(USER_EMAIL), is(nullValue()));
    }

    @Test
    public void shouldUpdateUser() {

        final User userToUpdate = new User(USER_EMAIL, PASS_CODE);
        userDao.updateUser(userToUpdate);

        assertThat(userToUpdate, is(notNullValue()));
        assertThat(userToUpdate.getEmail(), is(equalTo(USER_EMAIL)));

        userToUpdate.setEmail("update@email.com");

        userDao.updateUser(userToUpdate);

        final User updatedUser = getOnlyUser();
        assertThat(updatedUser, is(notNullValue()));
        assertThat(updatedUser.getEmail(), is(equalTo("update@email.com")));
    }

    @Test
    public void shouldRetrieveAllUsers() {

        addUser("user_1@user.com", "1111");
        addUser("user_2@user.com", "2222");

        final Set<User> users = userDao.findUsers();

        assertThat(users, is(notNullValue()));
        assertThat(users.size(), is(equalTo(2)));
    }

    @Test
    public void shouldAnEmptyCollectionIfNoUsersExist() {
        assertThat(userDao.findUsers(), is(empty()));
    }

    private int addUser(final String userName, final String passCode) {
        return sessionFactory.getCurrentSession().createSQLQuery(String.format("INSERT INTO BOOKER.USER (email, passcode) VALUES ('%s', '%s')", userName, passCode)).executeUpdate();
    }

    @Before
    public void init() {
        sessionFactory.getCurrentSession().createQuery("delete from UserActivity").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("delete from Activity").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("delete from User").executeUpdate();
        assertNoUsersPresent();
    }

    private void assertNoUsersPresent() {
        final List<Activity> allActivities = sessionFactory.getCurrentSession().createQuery("from User").list();
        assertThat(allActivities, is(empty()));
    }

}