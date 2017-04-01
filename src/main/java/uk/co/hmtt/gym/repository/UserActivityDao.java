package uk.co.hmtt.gym.repository;

import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.model.UserActivity;

import java.util.List;

public interface UserActivityDao {

    void addScheduledBookingRequest(UserActivity userActivity);

    List<UserActivity> getAllScheduledBookingRequests();

    UserActivity getScheduledBookingRequest(User user, Activity activity);

    List<UserActivity> getScheduledBookingRequests(User user);

    void updateScheduledBookingRequest(UserActivity userActivity);

    void deleteScheduledBookingRequest(UserActivity userActivity);

    List<Exclusion> getExclusions(final UserActivity userActivity);

    void addExclusion(final Exclusion exclusion);

    void deleteExclusion(final Exclusion exclusion);

}
