package uk.co.hmtt.gym.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.hmtt.gym.app.service.UserActivityService;

@Controller
public class ScheduleController extends GymController {

    public static final String SCHEDULE = "schedule";
    @Autowired
    private UserActivityService userActivityService;

    @RequestMapping(value = {"/schedule"}, method = RequestMethod.GET)
    public String userSchedule(final Model model) {
        model.addAttribute("existingBookings", userActivityService.getBookingRequest(getUser()));
        return SCHEDULE;
    }

}
