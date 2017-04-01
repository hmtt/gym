package uk.co.hmtt.gym.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.BookingForm;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.UserActivity;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;
import uk.co.hmtt.gym.web.validators.BookingFormValidator;

import javax.validation.Valid;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Handles the class booking form.
 * This includes -
 * <ul>
 * <li>Adding the class to the booking schedule if auto enrol is turned on</li>
 * <li>Removing the class from the booking schedule if auto enrol is turned off</li>
 * <li>Excluding a scheduled class on a specified date</li>
 * </ul>
 */
@Controller
public class BookClassController extends GymController {

    public static final String ACTIVITY = "activity";
    public static final String BOOKING_REQUEST = "bookingRequest";
    public static final String EXCLUSIONS = "exclusions";
    public static final String BOOKING_FORM = "bookingForm";
    public static final String EXISTING_BOOKINGS = "existingBookings";
    public static final String EXCLUSION_DATE = "exclusionDate";
    public static final String BOOK = "book";
    public static final String REDIRECT_GYM_ACTIVITIES_BOOK = "redirect:/gym/activities/book/";

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private BookingFormValidator bookingFormValidator;

    /**
     * Process a request to schedule/un-schedule a class
     */
    @RequestMapping(value = "/activities/book/{id:.+}", method = RequestMethod.POST)
    public String bookingForm(@Valid @ModelAttribute(BOOKING_FORM) BookingForm bookingForm,
                              BindingResult result, Model model, @PathVariable("id") String id) {

        final Activity activity = activityService.getActivity(Integer.parseInt(id));
        final UserActivity bookingRequest = userActivityService.getBookingRequest(getUser(), activity);

        bookingFormValidator.validate(bookingForm, result);
        if (result.hasErrors()) {
            populateErrorResponse(model, activity, bookingRequest);
            return BOOK;
        }

        processBookingRequest(bookingForm, activity, bookingRequest);

        final UserActivity updatedBookingRequest = userActivityService.getBookingRequest(getUser(), activity);

        processExclusionDateRequest(bookingForm, result, updatedBookingRequest);

        if (updatedBookingRequest != null) {
            model.addAttribute(EXCLUSIONS, updatedBookingRequest.getExclusions());
        }

        model.addAttribute(ACTIVITY, activity);
        model.addAttribute(BOOKING_REQUEST, updatedBookingRequest);
        model.addAttribute(EXISTING_BOOKINGS, userActivityService.getBookingRequest(getUser()));

        return REDIRECT_GYM_ACTIVITIES_BOOK + id;


    }

    private void processExclusionDateRequest(@Valid @ModelAttribute(BOOKING_FORM) BookingForm bookingForm, BindingResult result, UserActivity updatedBookingRequest) {
        if (classIsNotCurrentlyScheduled(bookingForm.getExclusionDate(), updatedBookingRequest)) {
            result.rejectValue(EXCLUSION_DATE, "bookingform.no.booking", "Please schedule this first.");
        } else if (classShouldBeExcludedExcluded(bookingForm.getExclusionDate())) {
            final Exclusion exclusion = userActivityService.addExclusion(updatedBookingRequest, bookingForm.getExclusionDate());
            updatedBookingRequest.getExclusions().add(exclusion);
        }
    }

    private void processBookingRequest(@Valid @ModelAttribute(BOOKING_FORM) BookingForm bookingForm, Activity activity, UserActivity bookingRequest) {
        if (userWishesToScheduleAClassThatIsNotAlreadyScheduled(bookingForm, bookingRequest)) {
            userActivityService.createBookingActivity(activity, getUser());
        } else if (userWishesCancelAClassThatIsAlreadyScheduled(bookingForm, bookingRequest)) {
            userActivityService.removeBookingRequest(bookingRequest);
        }
    }

    private boolean userWishesCancelAClassThatIsAlreadyScheduled(@Valid @ModelAttribute(BOOKING_FORM) BookingForm bookingForm, UserActivity bookingRequest) {
        return !bookingForm.isBooked() && bookingRequest != null;
    }

    private boolean userWishesToScheduleAClassThatIsNotAlreadyScheduled(@Valid @ModelAttribute(BOOKING_FORM) BookingForm bookingForm, UserActivity bookingRequest) {
        return bookingForm.isBooked() && bookingRequest == null;
    }

    private void populateErrorResponse(Model model, Activity activity, UserActivity bookingRequest) {
        model.addAttribute(ACTIVITY, activity);
        if (bookingRequest != null) {
            model.addAttribute(EXCLUSIONS, bookingRequest.getExclusions());
            model.addAttribute(EXISTING_BOOKINGS, userActivityService.getBookingRequest(getUser()));
        }
    }

    private boolean classIsNotCurrentlyScheduled(final String exclusionDate, UserActivity updatedBookingRequest) {
        return isNotEmpty(exclusionDate) && updatedBookingRequest == null;
    }

    private boolean classShouldBeExcludedExcluded(final String exclusionDate) {
        return isNotEmpty(exclusionDate);
    }

}
