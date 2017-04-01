package uk.co.hmtt.gym.provider.thepeak;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.app.exceptions.GymException;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.ActivityService;
import uk.co.hmtt.gym.app.service.UserService;
import uk.co.hmtt.gym.provider.thepeak.process.ActivityPageAction;
import uk.co.hmtt.gym.provider.thepeak.process.ActivityTimeAction;
import uk.co.hmtt.gym.provider.thepeak.process.HomePageSelection;
import uk.co.hmtt.gym.provider.thepeak.process.Logon;

import java.util.Set;

@Component
public class AvailableClasses {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableClasses.class);

    @Value("${gym.url}")
    private String gymUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private Logon logon;

    @Autowired
    private HomePageSelection homePage;

    @Autowired
    private ActivityPageAction activityPageAction;

    @Autowired
    private ActivityTimeAction activityTimeAction;

    @Autowired
    private WebDriverFactory webDriverFactory;

    public void find() {

        final WebDriver driver = webDriverFactory.newInstance();
        driver.get(gymUrl);

        // Logon
        final Set<User> users = userService.getUsers();
        if (users.isEmpty()) {
            throw new GymException("Could not derive a list of users; required to logon to website");
        }
        final User user = users.iterator().next();

        logon.logon(driver, user);
        homePage.makeABooking(driver);
        final Set<String> availableActivities = activityPageAction.readAllActivities(driver);

        int numberOfActivities = 0;
        for (String activity : availableActivities) {
            homePage.makeABooking(driver);
            activityPageAction.clickOnLink(driver, activity);
            final Set<String> times = activityTimeAction.readAllTimes(driver);
            for (String time : times) {
                activityService.addActivity(activity, time);
                numberOfActivities++;
            }
        }

        LOGGER.debug("Number of activities found {}", numberOfActivities);

        driver.quit();

    }

}
