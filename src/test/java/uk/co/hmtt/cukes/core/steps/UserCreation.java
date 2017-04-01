package uk.co.hmtt.cukes.core.steps;

import cucumber.api.java.en.Given;
import uk.co.hmtt.cukes.core.entities.EntityDao;
import uk.co.hmtt.cukes.core.entities.User;
import uk.co.hmtt.cukes.core.model.BookingTable;

import java.util.List;

import static uk.co.hmtt.cukes.core.entities.ActivityEntity.buildActivityEntity;
import static uk.co.hmtt.cukes.core.entities.User.buildDefaultAuthorisedUser;

/**
 * Created by swilson on 21/08/16.
 */
public class UserCreation extends GymStep {

    @Given("^an authorised user and available classes$")
    public void an_authorised_user_and_available_classes(List<BookingTable> booking) throws Throwable {
        persistDefaultUser();
        persistClass(booking);
        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY);
    }

    @Given("^a user that is authorised to use the site$")
    public void a_user_that_is_authorised_to_use_the_site() throws Throwable {
        persistDefaultUser();
        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY);
    }

    private void persistDefaultUser() {
        final User user = buildDefaultAuthorisedUser();
        user.persist();
        runtimeScope.setUser(user);
    }

    @Given("^a user that is authorised to use the site but has previously entered incorrect details$")
    public void a_user_that_is_authorised_to_use_the_site_but_has_previously_entered_incorrect_details() throws Throwable {
        final User user = buildDefaultAuthorisedUser().withFailedToLogInCount(1).persist();
        runtimeScope.setUser(user);
    }

    private void persistClass(List<BookingTable> bookings) {
        for (BookingTable booking : bookings) {
            buildActivityEntity().withClassName(booking.getName()).withClassDate(booking.getSession()).persist();
        }
    }


}
