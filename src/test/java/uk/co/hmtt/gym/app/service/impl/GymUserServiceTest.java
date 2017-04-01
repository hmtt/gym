package uk.co.hmtt.gym.app.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.repository.UserDao;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GymUserServiceTest {

    @Mock
    private UserDao userDao;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private GymUserService gymUserService;

    @Test
    public void shouldRetrieveUserByEmailAddress() {
        when(userDao.findUser(anyString())).thenReturn(new User());
        assertThat(gymUserService.getUser("email"), is(notNullValue()));
    }

    @Test
    public void shouldRetrieveAllUsers() {
        when(userDao.findUsers()).thenReturn(Collections.singleton(new User()));
        assertThat(gymUserService.getUsers(), is(not(empty())));
    }

    @Test
    public void shouldResetFailedLogonMarkerUponSuccessfulLogon() {

        final User user = new User();
        user.setFailedToLoginCount(1);

        gymUserService.updateUserForSuccessfulLogon(user);

        verify(userDao, times(1)).updateUser(userCaptor.capture());
        assertThat(userCaptor.getValue().getFailedToLoginCount(), is(equalTo(0)));

    }

    @Test
    public void shouldIncrementFailedLogonCountForUnsuccessfulLogon() {

        final User user = new User();
        user.setFailedToLoginCount(1);

        gymUserService.updateUserForFailedLogon(user);

        verify(userDao, times(1)).updateUser(userCaptor.capture());
        assertThat(userCaptor.getValue().getFailedToLoginCount(), is(equalTo(2)));

    }

}