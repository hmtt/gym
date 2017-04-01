package uk.co.hmtt.gym.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.hmtt.gym.app.model.Activity;
import uk.co.hmtt.gym.app.model.BookingForm;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.UserActivity;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserActivityService;
import uk.co.hmtt.gym.web.validators.BookingFormValidator;

import java.util.Set;

@Controller
public class BookingController extends GymController {

    public static final String ACTIVITY = "activity";
    public static final String BOOKING_REQUEST = "bookingRequest";
    public static final String EXCLUSIONS = "exclusions";
    public static final String BOOKING_FORM = "bookingForm";
    public static final String EXISTING_BOOKINGS = "existingBookings";
    public static final String BOOK = "book";

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private BookingFormValidator bookingFormValidator;

    /**
     * Returns the information required to populate the booking form.
     * Also removes any exclusion specified as a query string parameter
     *
     * @param model
     * @param id
     * @param cancel
     * @return
     */
    @RequestMapping(value = "/activities/book/{id:.+}", method = RequestMethod.GET)
    public String bookActivity(final Model model, @PathVariable("id") String id, @RequestParam(value = "cancel", required = false) String cancel) {

        final Activity activity = activityService.getActivity(Integer.parseInt(id));

        // Cancel an exclusion if specified on query string
        if (cancel != null) {
            final UserActivity bookingRequest = userActivityService.getBookingRequest(getUser(), activity);
            userActivityService.cancelExistingExclusion(cancel, bookingRequest);
        }

        final UserActivity updatedBookingRequest = userActivityService.getBookingRequest(getUser(), activity);

        model.addAttribute(ACTIVITY, activity);
        model.addAttribute(BOOKING_REQUEST, updatedBookingRequest);
        model.addAttribute(EXCLUSIONS, retrieveExclusions(updatedBookingRequest));
        model.addAttribute(BOOKING_FORM, createBookingForm(updatedBookingRequest));
        model.addAttribute(EXISTING_BOOKINGS, userActivityService.getBookingRequest(getUser()));

        return BOOK;

    }

    private Set<Exclusion> retrieveExclusions(UserActivity updatedBookingRequest) {
        Set<Exclusion> exclusions = null;
        if (updatedBookingRequest != null) {
            exclusions = updatedBookingRequest.getExclusions();
        }
        return exclusions;
    }

    private BookingForm createBookingForm(UserActivity updatedBookingRequest) {
        final BookingForm bookingForm = new BookingForm();
        if (updatedBookingRequest != null) {
            bookingForm.setBooked(true);
        }
        return bookingForm;
    }

}
