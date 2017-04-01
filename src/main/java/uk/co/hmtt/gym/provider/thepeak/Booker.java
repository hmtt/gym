package uk.co.hmtt.gym.provider.thepeak;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.app.model.Exclusion;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.model.UserActivity;
import uk.co.hmtt.gym.app.service.UserActivityService;
import uk.co.hmtt.gym.provider.thepeak.process.*;

import java.text.ParseException;
import java.util.*;

import static uk.co.hmtt.gym.app.utilities.DateUtility.isDateExcluded;

@Component
public class Booker {

    final Logger logger = LoggerFactory.getLogger(Booker.class);

    @Value("${gym.url}")
    private String gymUrl;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Autowired
    private Logon logon;

    @Autowired
    private BookClass bookClass;

    @Autowired
    private HomePageSelection homePageSelection;

    @Autowired
    private ActivityPageAction activityPageAction;

    @Autowired
    private ActivityTimeAction activityTimeAction;

    public void book() {
        final Map<User, Set<UserActivity>> bookingRequests = userActivityService.getBookingRequests();

        for (Map.Entry<User, Set<UserActivity>> user : bookingRequests.entrySet()) {
            if (!user.getKey().isEnabled()) {
                logger.debug("Skipping user {}. Not enabled.", user.getKey().getEmail());
                continue;
            }
            final WebDriver driver = webDriverFactory.newInstance();
            try {
                driver.get(gymUrl);
                logon.logon(driver, user.getKey().getEmail(), user.getKey().getPasscode());
                bookClasses(driver, user.getKey(), bookingRequests.get(user.getKey()));
            } finally {
                driver.quit();
            }
        }

    }

    private void bookClasses(WebDriver driver, User user, Set<UserActivity> activities) {
        for (UserActivity activity : activities) {
            bookClass(driver, user, activity);
        }
    }

    private void bookClass(WebDriver driver, User user, UserActivity activity) {
        try {
            homePageSelection.makeABooking(driver);
            activityPageAction.clickOnLink(driver, activity.getActivity().getClassName());
            activityTimeAction.clickOnTime(driver, activity.getActivity().getClassDate());

            final String classDate = bookClass.readClassDate(driver);

            if (dateIsExcluded(activity, classDate)) {
                logger.debug("Did not book '" + activity.getActivity().getClassName() + "' at '" + activity.getActivity().getClassDate() + "' as excluded date");
                return;
            }

            bookClass.book(driver);

            activity.setLastBooked(new Date());
            userActivityService.updateBookingRequest(activity);

        } catch (RuntimeException e) {
            logger.error("Could not book '" + activity.getActivity().getClassName() + "' for '" + activity.getActivity().getClassDate() + "' for client '" + user.getEmail() + "'", e);
        }
    }

    private boolean dateIsExcluded(UserActivity activity, String classTime) {
        if (CollectionUtils.isNotEmpty(activity.getExclusions())) {
            final String scrappedDate = classTime.trim();
            try {

                final List<Calendar> excludedDates = new ArrayList<>();
                for (Exclusion exclusion : activity.getExclusions()) {
                    final Calendar instance = Calendar.getInstance();
                    instance.setTime(exclusion.getExclusionDate());
                    excludedDates.add(instance);
                }

                if (isDateExcluded(scrappedDate, excludedDates)) {
                    return true;
                }
            } catch (ParseException e) {
                logger.error("Could not process exclusion", e);
            }
        }
        return false;
    }

}
