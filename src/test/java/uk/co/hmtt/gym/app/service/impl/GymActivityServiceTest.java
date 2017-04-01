package uk.co.hmtt.gym.app.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.repository.ActivityDao;
import uk.co.hmtt.gym.repository.UserDao;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GymActivityServiceTest {

    @Mock
    private ActivityDao activityDao;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private GymActivityService gymActivityService;

    @Test
    public void shouldAddActivity() {
        gymActivityService.addActivity("class_name", "class_date");
        verify(activityDao, times(1)).addActivity("class_name", "class_date");
    }

    @Test
    public void shouldRetrieveActivityById() {
        when(activityDao.getActivity(anyInt())).thenReturn(new Activity());
        assertThat(gymActivityService.getActivity(1), is(notNullValue()));
    }

    @Test
    public void shouldRetrieveAllActivities() {
        when(activityDao.getAllActivities()).thenReturn(Collections.singletonList(new Activity()));
        assertThat(gymActivityService.getAllActivities(), is(not(empty())));
    }

    @Test
    public void shouldRetrieveTimesForASpecifiedActivity() {
        when(activityDao.getTimesForActivity("class_name")).thenReturn(Collections.singletonList(new Activity()));
        assertThat(gymActivityService.getTimesForActivity("class_name"), is(not(empty())));
    }

}