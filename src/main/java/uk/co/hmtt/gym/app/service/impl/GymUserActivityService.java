package uk.co.hmtt.gym.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.ExclusionId;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;
import uk.co.hmtt.gym.repository.UserActivityDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;

@Component
public class GymUserActivityService implements UserActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GymUserActivityService.class);

    @Autowired
    private UserActivityDao userActivity;

    @Autowired
    private ActivityService activityService;

    @Override
    @Transactional
    public Map<User, Set<uk.co.hmtt.gym.app.model.UserActivity>> getBookingRequests() {
        final List<uk.co.hmtt.gym.app.model.UserActivity> bookingRequests = userActivity.getAllScheduledBookingRequests();

        final HashMap<User, Set<uk.co.hmtt.gym.app.model.UserActivity>> response = new HashMap<>();

        if (bookingRequests == null) {
            return response;
        }

        addExceptions(bookingRequests, response);
        return response;
    }

    private void addExceptions(List<uk.co.hmtt.gym.app.model.UserActivity> bookingRequests, HashMap<User, Set<uk.co.hmtt.gym.app.model.UserActivity>> response) {
        for (uk.co.hmtt.gym.app.model.UserActivity booking : bookingRequests) {
            if (booking != null) {
                getExclusions(booking);
                Set<uk.co.hmtt.gym.app.model.UserActivity> activities = response.get(booking.getUser());
                addUserToResponseCollection(response, booking, activities);
                addBookingToUsersRequests(response, booking);
            }
        }
    }

    @Override
    @Transactional
    public uk.co.hmtt.gym.app.model.UserActivity getBookingRequest(User user, Activity activity) {
        final uk.co.hmtt.gym.app.model.UserActivity bookingRequest = userActivity.getScheduledBookingRequest(user, activity);
        getExclusions(bookingRequest);
        return bookingRequest;
    }

    @Override
    @Transactional
    public List<uk.co.hmtt.gym.app.model.UserActivity> getBookingRequest(User user) {
        final List<uk.co.hmtt.gym.app.model.UserActivity> bookingRequests = userActivity.getScheduledBookingRequests(user);
        for (uk.co.hmtt.gym.app.model.UserActivity userActivityBooking : bookingRequests) {
            getExclusions(userActivityBooking);
        }
        return bookingRequests;
    }

    @Override
    @Transactional
    public void addBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivityModel) {
        userActivity.addScheduledBookingRequest(userActivityModel);
    }

    @Override
    @Transactional
    public void removeBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivityModel) {
        userActivity.deleteScheduledBookingRequest(userActivityModel);
    }

    @Override
    @Transactional
    public void removeExclusion(uk.co.hmtt.gym.app.model.UserActivity userActivityModel, Date excludedDate) {
        final Set<Exclusion> exclusions = userActivityModel.getExclusions();
        if (exclusions != null) {
            for (Exclusion exclusion : exclusions) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(exclusion.getExclusionDate());
                cal2.setTime(excludedDate);
                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

                if (sameDay) {
                    userActivity.deleteExclusion(exclusion);
                    break;
                }

            }
        }
    }

    @Override
    @Transactional
    public void addExclusion(Exclusion exclusion) {
        userActivity.addExclusion(exclusion);
    }

    @Override
    @Transactional
    public Exclusion addExclusion(uk.co.hmtt.gym.app.model.UserActivity bookingRequest, String date) {
        final Exclusion exclusion = new Exclusion();
//        exclusion.setUserActivity(bookingRequest);
        final ExclusionId exclusionId = new ExclusionId();
        exclusionId.setUserActivity(bookingRequest);
        exclusion.setPk(exclusionId);
        try {
            final Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            exclusion.setExclusionDate(parsedDate);
        } catch (ParseException e) {
            LOGGER.error("Could not parse date {}", date, e);
        }
        addExclusion(exclusion);
        return exclusion;
    }

    @Override
    @Transactional
    public void updateBookingRequest(uk.co.hmtt.gym.app.model.UserActivity userActivityModel) {
        userActivity.updateScheduledBookingRequest(userActivityModel);
    }

    @Override
    @Transactional
    public void createBookingActivity(Activity activity, User user) {

        final uk.co.hmtt.gym.app.model.UserActivity newActivity = new uk.co.hmtt.gym.app.model.UserActivity();
        newActivity.setActivity(activity);
        newActivity.setUser(user);

        final List<Activity> allActivities = activityService.getAllActivities();
        final List<Integer> activityIds = new ArrayList<>();
        for (Activity activityId : allActivities) {
            activityIds.add(activityId.getId());
        }

        sort(activityIds);
        reverse(activityIds);
        addBookingRequest(newActivity);

    }

    @Override
    @Transactional
    public void cancelExistingExclusion(String cancelDate, uk.co.hmtt.gym.app.model.UserActivity bookingRequest) {
        try {
            final Date date = new SimpleDateFormat("yyyy-MM-dd").parse(cancelDate);
            removeExclusion(bookingRequest, date);
        } catch (ParseException e) {
            LOGGER.error("Could not parse string {} to date", cancelDate, e);
        }
    }

    private void addBookingToUsersRequests(HashMap<User, Set<uk.co.hmtt.gym.app.model.UserActivity>> response, uk.co.hmtt.gym.app.model.UserActivity booking) {
        response.get(booking.getUser()).add(booking);
    }

    private void addUserToResponseCollection(HashMap<User, Set<uk.co.hmtt.gym.app.model.UserActivity>> response, uk.co.hmtt.gym.app.model.UserActivity booking, Set<uk.co.hmtt.gym.app.model.UserActivity> activities) {
        if (activities == null) {
            response.put(booking.getUser(), new HashSet<>());
        }
    }

    private void getExclusions(uk.co.hmtt.gym.app.model.UserActivity booking) {
        if (booking != null) {
            final List<Exclusion> exclusions = userActivity.getExclusions(booking);
            booking.setExclusions(new HashSet<>(exclusions));
        }
    }
}
