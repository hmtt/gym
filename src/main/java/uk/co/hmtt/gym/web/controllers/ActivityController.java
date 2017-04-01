package uk.co.hmtt.gym.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;

/**
 * Controller for listing a specific activity and available times
 */
@Controller
public class ActivityController extends GymController {

    public static final String ACTIVITY_NAME = "activityName";
    public static final String ACTIVITY_TIMES = "activityTimes";
    public static final String EXISTING_BOOKINGS = "existingBookings";
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    /**
     * List all available class times available
     *
     * @param model
     * @param className
     * @return
     */
    @RequestMapping(value = "/activities/{className:.+}", method = RequestMethod.GET)
    public String provideActivityTimesList(final Model model, @PathVariable("className") String className) {
        model.addAttribute(ACTIVITY_NAME, className);
        model.addAttribute(ACTIVITY_TIMES, activityService.getTimesForActivity(className));
        model.addAttribute(EXISTING_BOOKINGS, userActivityService.getBookingRequest(getUser()));
        return "activitytime";
    }

}
