package uk.co.hmtt.gym.provider.thepeak.process;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.provider.thepeak.pageobjects.ActivityTimePage;

import java.util.HashSet;
import java.util.Set;

@Component
public class ActivityTimeAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTimeAction.class);

    public Set<String> readAllTimes(final WebDriver driver) {
        final ActivityTimePage activityTimePage = PageFactory.initElements(driver, ActivityTimePage.class);
        Set<String> times = new HashSet<>();
        try {
            activityTimePage.waitForPageToLoad(driver);
            times = activityTimePage.readAvailableTimes();
        } catch (TimeoutException e) {
            LOGGER.debug("No activities found", e);
        }
        return times;
    }

    public void clickOnTime(final WebDriver driver, final String time) {
        PageFactory.initElements(driver, ActivityTimePage.class).clickOnTime(time);
    }

}
