package uk.co.hmtt.gym.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.sort;
import static uk.co.hmtt.gym.app.utilities.CollectionsUtil.safeIterator;

/**
 * Controller for listing all available activities
 */
@Controller
public class ActivitiesController extends GymController {

    public static final String EXISTING_BOOKINGS = "existingBookings";
    public static final String ALL_ACTIVITIES = "allActivities";

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    /**
     * Provide a full list of activities available to schedule a booking against
     *
     * @param model
     * @return a reference to the rendering JSP
     */
    @RequestMapping(method = RequestMethod.GET)
    public String retrieveActivities(final Model model) {
        model.addAttribute(ALL_ACTIVITIES, sortActivitiesIntoUniqueCollection());
        model.addAttribute(EXISTING_BOOKINGS, userActivityService.getBookingRequest(getUser()));
        return "activities";
    }

    private List<String> sortActivitiesIntoUniqueCollection() {
        final Set<String> uniqueActivities = new HashSet<>();
        for (Activity activity : safeIterator(activityService.getAllActivities())) {
            uniqueActivities.add(activity.getClassName());
        }
        final List<String> sortedActivities = new ArrayList<>(uniqueActivities);
        sort(sortedActivities);
        return sortedActivities;
    }

}
