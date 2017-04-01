package uk.co.hmtt.gym.app.service;

import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.model.UserActivity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserActivityService {

    Map<User, Set<UserActivity>> getBookingRequests();

    uk.co.hmtt.gym.app.model.UserActivity getBookingRequest(User user, Activity activity);

    List<UserActivity> getBookingRequest(User user);

    void addBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivity);

    void removeBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivity);

    void removeExclusion(uk.co.hmtt.gym.app.model.UserActivity userActivity, Date date);

    void addExclusion(final Exclusion exclusion);

    Exclusion addExclusion(UserActivity bookingRequest, String date);

    void updateBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivity);

    void createBookingActivity(Activity activity, User user);

    void cancelExistingExclusion(String cancelDate, UserActivity bookingRequest);

}
