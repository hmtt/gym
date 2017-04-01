package uk.co.hmtt.gym.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.UserActivity;
import uk.co.hmtt.gym.app.model.UserActivityId;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;
import uk.co.hmtt.gym.config.web.GymUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ActivitiesControllerTest.WebConfig.class})
public class ActivitiesControllerTest {

    public static final String Z_CLASS = "Z Class";
    public static final String A_CLASS = "A Class";
    private MockMvc mockMvc;

    private final static ResultMatcher OK = MockMvcResultMatchers.status().isOk();

    private final static ResultMatcher ACTIVITIES = MockMvcResultMatchers.forwardedUrl("activities");

    private final static  MockHttpServletRequestBuilder BUILDER = MockMvcRequestBuilders.get("/gym");

    @InjectMocks
    private ActivitiesController controller;

    @Mock
    private ActivityService activityService;

    @Mock
    private UserActivityService userActivityService;

    @Mock
    private Authentication authentication;

    @Mock
    private GymUser gymUser;

    @Mock
    private SecurityContext securityContext;

    @Test
    public void shouldRetrieveAllActivities() throws Exception {

        final UserActivity userActivity = createUserActivity();
        when(userActivityService.getBookingRequest(any())).thenReturn(Collections.singletonList(userActivity));
        ResultMatcher bookingRequests = model().attribute("existingBookings", Collections.singletonList(userActivity));

        this.mockMvc.perform(BUILDER).andExpect(OK).andExpect(ACTIVITIES).andExpect(bookingRequests);

    }

    @Test
    public void shouldRetrieveAllActivitiesOrderedByClassName() throws Exception {

        mockUnorderedActivities(createActivity(Z_CLASS), createActivity(A_CLASS));

        final ArrayList<String> expected = new ArrayList<>();
        expected.add(A_CLASS);
        expected.add(Z_CLASS);
        ResultMatcher orderedActivities = model().attribute("allActivities", expected);

        this.mockMvc.perform(BUILDER).andExpect(OK).andExpect(ACTIVITIES).andExpect(orderedActivities);

    }

    private void mockUnorderedActivities(Activity... activity) {
        final ArrayList<Activity> unorderedActivities = new ArrayList(Arrays.asList(activity));
        when(activityService.getAllActivities()).thenReturn(unorderedActivities);
    }

    private Activity createActivity(String className) {
        final Activity activity = new Activity();
        activity.setClassName(className);
        return activity;
    }


    private UserActivity createUserActivity() {
        final UserActivity userActivity = new UserActivity();
        final UserActivityId userActivityId = new UserActivityId();
        final Activity activity = new Activity();
        userActivityId.setActivity(activity);
        final uk.co.hmtt.gym.app.model.User user = new uk.co.hmtt.gym.app.model.User();
        userActivityId.setUser(user);
        userActivity.setPk(userActivityId);
        return userActivity;
    }

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSecurity();
    }

    private void mockSecurity() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(gymUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Configuration
    public static class WebConfig {

    }

}