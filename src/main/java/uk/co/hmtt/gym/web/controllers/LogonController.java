package uk.co.hmtt.gym.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;
import uk.co.hmtt.gym.web.validators.BookingFormValidator;

@Controller
@RequestMapping()
public class LogonController {

    public static final String REDIRECT_GYM = "redirect:/gym";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String ANONYMOUS_USER = "anonymousUser";
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private BookingFormValidator bookingFormValidator;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirect(final Model model) {
        return userIsAlreadyLoggedIn() ? REDIRECT_GYM : REDIRECT_LOGIN;
    }

    private boolean userIsAlreadyLoggedIn() {
        return !ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

}
