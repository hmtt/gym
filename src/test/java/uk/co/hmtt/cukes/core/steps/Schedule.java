package uk.co.hmtt.cukes.core.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import uk.co.hmtt.cukes.core.entities.ActivityEntity;
import uk.co.hmtt.cukes.core.entities.EntityDao;
import uk.co.hmtt.cukes.core.model.BookingTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static uk.co.hmtt.cukes.core.entities.User.buildDefaultAuthorisedUser;
import static uk.co.hmtt.cukes.core.utilities.ServiceClient.callService;

/**
 * Created by swilson on 02/09/16.
 */
public class Schedule extends GymStep {

    @Value("${services.url}")
    private String servicesUrl;

    @Given("^no activities are registered$")
    public void no_activities_are_registered() throws Throwable {
        buildDefaultAuthorisedUser().persist();
        final List<ActivityEntity> currentActivities = entityDao.fetch(ActivityEntity.class);
        assertThat(currentActivities, is(Collections.<ActivityEntity>emptyList()));
    }

    @When("^the automated activity checker is run$")
    public void the_automated_activity_checker_is_run() throws Throwable {
        callService(servicesUrl + "/execute?type=available", "admin@hmtt.co.uk", "1111");
    }

    @When("^the automated booking function is run$")
    public void the_automated_booking_function_is_run() throws Throwable {
        callService(servicesUrl + "/execute?type=booker", "userOne@hmtt.co.uk", "1111");
    }

    @Then("^activities are registered$")
    public void activities_are_registered(List<BookingTable> expectedClasses) throws Throwable {
        printTables(EntityDao.TABLE_NAME.ACTIVITY);
        final List<ActivityEntity> activityEntities = entityDao.fetch(ActivityEntity.class);
        final List<BookingTable> actualClasses = convertActivityEntitiesToBookingTable(activityEntities);
        compareLists(expectedClasses, actualClasses);
    }

    @Then("^\"([^\"]*)\" classes are booked$")
    public void classes_are_booked(String email, List<BookingTable> expectedClasses) throws Throwable {
        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY);
        final List<ActivityEntity> activities = entityDao.getUser(email).getScheduledActivitiesActivities();
        final List<BookingTable> actualBookings = convertActivityEntitiesToBookingTable(activities);
        compareLists(expectedClasses, actualBookings);
    }

    private List<BookingTable> convertActivityEntitiesToBookingTable(List<ActivityEntity> activityEntities) {
        final List<BookingTable> bookingTables = new ArrayList<>();
        for (ActivityEntity activityEntity : activityEntities) {
            final BookingTable bookingTable = new BookingTable();
            bookingTable.setName(activityEntity.getClassName());
            bookingTable.setSession(activityEntity.getClassDate());
            bookingTables.add(bookingTable);
        }
        return bookingTables;
    }

}
