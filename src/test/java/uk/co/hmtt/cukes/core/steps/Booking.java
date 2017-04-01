package uk.co.hmtt.cukes.core.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriverFactory;
import uk.co.hmtt.cukes.core.entities.ActivityEntity;
import uk.co.hmtt.cukes.core.entities.EntityDao;
import uk.co.hmtt.cukes.core.entities.ExclusionEntity;
import uk.co.hmtt.cukes.core.entities.User;
import uk.co.hmtt.cukes.core.model.BookingTable;
import uk.co.hmtt.cukes.core.web.pageobjects.Schedule;
import uk.co.hmtt.cukes.core.web.process.*;
import uk.co.hmtt.gym.app.exceptions.GymException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static uk.co.hmtt.cukes.core.entities.ActivityEntity.buildActivityEntity;
import static uk.co.hmtt.cukes.core.entities.ExclusionEntity.buildExclusionEntity;
import static uk.co.hmtt.cukes.core.entities.User.buildDefaultAuthorisedUser;
import static uk.co.hmtt.gym.app.utilities.CollectionsUtil.safeIterator;

/**
 * Created by swilson on 21/08/16.
 */
public class Booking extends GymStep {

    final Logger LOGGER = LoggerFactory.getLogger(Booking.class);

    public static final String CLASS_NAME = "CLASS_NAME";
    public static final String CLASS_DATE = "CLASS_DATE";
    public static final String PASS_CODE = "1111";
    public static final String DATA_TABLE = "dataTable";

    @Autowired
    private Logon logon;

    @Autowired
    private ActivityTimeProcess activityTimeProcess;

    @Autowired
    private BookClassProcess bookClassProcess;

    @Autowired
    private CukesWebDriverFactory webDriverFactory;

    @Autowired
    private ActivitiesProcess activitiesProcess;

    @Autowired
    private LogonToGym logonToGym;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private ScheduleProcess scheduleProcess;

    @Value("${gymbooker.url}")
    private String gymBookerUrl;

    @When("^the user logs onto the site$")
    public void the_user_logs_onto_the_site() throws Throwable {
        logonToGym.logon(runtimeScope.getUser());
    }

    @When("^the user books class$")
    public void the_user_books_class(List<BookingTable> bookings) throws Throwable {
        for (BookingTable booking : bookings) {
            activitiesProcess.selectActivity(booking.getName());
            activityTimeProcess.selectActivityTime(booking.getSession());
            bookClassProcess.bookClass();
        }
    }

    @Then("^the chosen classes are scheduled to be booked$")
    public void the_chosen_classes_are_scheduled_to_be_booked(List<BookingTable> expectedBookings) throws Throwable {

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY, EntityDao.TABLE_NAME.EXCLUSION);

        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());
        assertThatUserAndActivitiesFound(expectedBookings, user);

        final List<BookingTable> actualList = convertDerivedActivitiesToBookingTableList(user.getScheduledActivitiesActivities());
        for (int i = 0; i < expectedBookings.size(); i++) {
            assertThat(actualList.get(i), is(equalTo(expectedBookings.get(i))));
        }
    }

    @Then("^the chosen classes are booked$")
    public void the_chosen_classes_are_booked(List<BookingTable> expectedBookings) throws Throwable {

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY, EntityDao.TABLE_NAME.EXCLUSION);

        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());
        assertThat(user, is(notNullValue()));
        assertThat(user.getBookedActivities(), is(notNullValue()));
        assertThat(user.getBookedActivities().size(), is(equalTo(expectedBookings.size())));

        final List<BookingTable> actualList = convertDerivedActivitiesToBookingTableList(user.getBookedActivities());
        for (int i = 0; i < expectedBookings.size(); i++) {
            assertThat(actualList.get(i), is(equalTo(expectedBookings.get(i))));
        }
    }

    @Then("^the classes that were not chosen are not scheduled to be booked$")
    public void the_classes_that_were_not_chosen_are_not_scheduled_to_be_booked(List<BookingTable> expectedNotBookedList) throws Throwable {

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY, EntityDao.TABLE_NAME.EXCLUSION);

        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());
        final List<BookingTable> actualList = convertDerivedActivitiesToBookingTableList(user.getScheduledActivitiesActivities());

        for (BookingTable actualBooking : actualList) {
            assertThat(format("Booking should not have been made %s", actualBooking.toString()), expectedNotBookedList.contains(actualBooking), is(false));
        }

    }

    @Then("^the classes that were not chosen are not booked$")
    public void the_classes_that_were_not_chosen_are_not_booked(List<BookingTable> expectedNotBookedList) throws Throwable {

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY, EntityDao.TABLE_NAME.EXCLUSION);

        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());
        final List<BookingTable> actualList = convertDerivedActivitiesToBookingTableList(user.getBookedActivities());

        for (BookingTable actualBooking : actualList) {
            assertThat(format("Booking should not have been made %s", actualBooking.toString()), expectedNotBookedList.contains(actualBooking), is(false));
        }

    }

    private void assertThatUserAndActivitiesFound(List<BookingTable> expectedBookings, User user) {
        assertThat(user, is(notNullValue()));
        assertThat(user.getScheduledActivitiesActivities(), is(notNullValue()));
        assertThat(user.getScheduledActivitiesActivities().size(), is(equalTo(expectedBookings.size())));
    }

    @Given("^a user with email \"([^\"]*)\" who has booked classes$")
    public void a_user_with_email_who_has_booked_classes(String email, List<BookingTable> bookings) throws Throwable {

        final User userEntity = buildDefaultAuthorisedUser()
                .withEmail(email)
                .withPassCode(PASS_CODE);

        for (BookingTable booking : bookings) {
            final ActivityEntity activity = buildActivityEntity()
                    .withClassName(booking.getName())
                    .withClassDate(booking.getSession());
            userEntity.withActivity(activity);
        }

        userEntity.persist();

        runtimeScope.setUser(userEntity);

    }

    @Given("^a user who has booked classes$")
    public void a_user_who_has_booked_classes(List<BookingTable> bookings) throws Throwable {

        final User userEntity = buildDefaultAuthorisedUser()
                .withPassCode(PASS_CODE);

        for (BookingTable booking : bookings) {
            final ActivityEntity activity = buildActivityEntity()
                    .withClassName(booking.getName())
                    .withClassDate(booking.getSession());
            userEntity.withActivity(activity);
        }

        userEntity.persist();

        runtimeScope.setUser(userEntity);

    }

    @Then("^chosen classes are scheduled on the web site$")
    public void chosen_classes_are_scheduled_on_the_web_site(List<BookingTable> expectedBookings) throws Throwable {
        final List<Schedule.ScheduledActivity> scheduledActivities = scheduleProcess.readBookings();
        final List<BookingTable> actualList = convertScheduledActivitiesToBookingTableList(scheduledActivities);
        for (int i = 0; i < expectedBookings.size(); i++) {
            assertThat(actualList.get(i), is(equalTo(expectedBookings.get(i))));
        }
    }

    private List<BookingTable> convertDerivedActivitiesToBookingTableList(List<ActivityEntity> activities) {
        final List<BookingTable> actualList = new ArrayList<>();
        for (ActivityEntity entity : activities) {
            final BookingTable bookingTable = new BookingTable();
            bookingTable.setName(entity.getClassName());
            bookingTable.setSession(entity.getClassDate());
            actualList.add(bookingTable);
        }
        return actualList;
    }

    private List<BookingTable> convertScheduledActivitiesToBookingTableList(List<Schedule.ScheduledActivity> activities) {
        final List<BookingTable> actualList = new ArrayList<>();
        for (Schedule.ScheduledActivity entity : activities) {
            final BookingTable bookingTable = new BookingTable();
            bookingTable.setName(entity.getClassName());
            bookingTable.setSession(entity.getClassTime());
            actualList.add(bookingTable);
        }
        return actualList;
    }

    @When("^clicks on the schedule link for \"([^\"]*)\" for \"([^\"]*)\"$")
    public void clicks_on_the_schedule_link_for_for(String className, String classTime) throws Throwable {
        scheduleProcess.navigateToScheduleScreen();
        for (Schedule.ScheduledActivity scheduledActivity : safeIterator(scheduleProcess.readBookings())) {
            if (className.equals(scheduledActivity.getClassName()) && classTime.equals(scheduledActivity.getClassTime())) {
                scheduledActivity.navigateToBooking();
                break;
            }
        }
    }

    @Then("^the user navigates to the booking screen for \"([^\"]*)\" for \"([^\"]*)\"$")
    public void the_user_navigates_to_the_booking_screen_for_for(String className, String classTime) throws Throwable {
        assertThat(bookClassProcess.readHeader(), containsString(className + ":" + classTime));
    }

    @Then("^the activities should be scheduled with exclusions$")
    public void the_activities_should_be_scheduled_with_exclusions(List<BookingTable> expectedExclusions) throws Throwable {

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY);

        final List<Schedule.Exclusion> exclusions = scheduleProcess.readExclusions();
        assertThat(exclusions.size(), is(equalTo(expectedExclusions.size())));

        final List<BookingTable> actualExclusions = convertScheduledExclusionsToBookingTable(exclusions);

        compareLists(expectedExclusions, actualExclusions);

    }


    private List<BookingTable> convertScheduledExclusionsToBookingTable(List<Schedule.Exclusion> exclusions) {
        final List<BookingTable> actualExclusions = new ArrayList<>();
        for (Schedule.Exclusion exclusion : exclusions) {
            final BookingTable bookingTable = new BookingTable();
            bookingTable.setName(exclusion.getClassName());
            bookingTable.setSession(exclusion.getClassTime());
            bookingTable.setExclusion(exclusion.getRequestedExclusion());
            actualExclusions.add(bookingTable);
        }
        return actualExclusions;
    }

    @When("^adds an exclusion$")
    public void adds_an_exclusion(List<BookingTable> exclusions) throws Throwable {
        for (BookingTable exclusion : exclusions) {
            activitiesProcess.selectActivity(exclusion.getDisplayName());
            activityTimeProcess.selectActivityTime(exclusion.getSession());
            final String[] exclusionParts = exclusion.getExclusion().split("/");
            bookClassProcess.addExclusion(exclusionParts[0], exclusionParts[1], exclusionParts[2], true);
        }
    }

    @Then("^the exclusion is added to the users schedule$")
    public void the_exclusion_is_added_to_the_users_schedule(List<BookingTable> expectedExclusions) throws Throwable {
        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY);
        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());

        final List<BookingTable> bookingTables = convertDataEntityToBookingTable(user);

        compareLists(expectedExclusions, bookingTables);

    }

    private List<BookingTable> convertDataEntityToBookingTable(User user) {
        final List<BookingTable> actualBookingTables = new ArrayList<>();
        for (ActivityEntity activity : user.getScheduledActivitiesActivities()) {
            for (ExclusionEntity exclusionEntity : activity.getExclusions()) {
                final BookingTable bookingTable = new BookingTable();
                bookingTable.setName(activity.getClassName());
                bookingTable.setSession(activity.getClassDate());
                bookingTable.setExclusion(new SimpleDateFormat("dd/MM/yyyy").format(exclusionEntity.getExclusionDate()));
                actualBookingTables.add(bookingTable);
            }
        }
        return actualBookingTables;
    }

    @When("^adds an exclusion using the date picker$")
    public void adds_an_exclusion_using_the_date_picker(List<BookingTable> exclusions) throws Throwable {

        for (BookingTable exclusion : exclusions) {
            activitiesProcess.selectActivity(exclusion.getName());
            activityTimeProcess.selectActivityTime(exclusion.getSession());
            final String[] exclusionParts = exclusion.getExclusion().split("/");
            bookClassProcess.addExclusion(exclusionParts[0], exclusionParts[1], exclusionParts[2], true);
        }
    }

    @Given("^a user who has booked classes with exclusions$")
    public void a_user_who_has_booked_classes_with_exclusions(List<BookingTable> dataTable) throws Throwable {

        final User userEntity = buildDefaultAuthorisedUser();

        for (BookingTable row : dataTable) {
            Date exclusionDate;
            try {
                if (!"none".equals(row.getExclusion())) {
                    exclusionDate = new SimpleDateFormat("dd/MM/yyyy").parse(row.getExclusion());
                    final ActivityEntity activity = buildActivityEntity()
                            .withClassName(row.getName())
                            .withClassDate(row.getSession())
                            .withExclusion(buildExclusionEntity().withExclusionDate(exclusionDate));
                    userEntity.withActivity(activity);
                } else {
                    final ActivityEntity activity = buildActivityEntity()
                            .withClassName(row.getName())
                            .withClassDate(row.getSession());
                    userEntity.withActivity(activity);
                }
            } catch (ParseException e) {
                throw new GymException(e);
            }

        }
        userEntity.persist();

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY, EntityDao.TABLE_NAME.EXCLUSION);

        runtimeScope.setUser(userEntity);

    }

}
