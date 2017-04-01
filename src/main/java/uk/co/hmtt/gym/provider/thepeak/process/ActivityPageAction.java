package uk.co.hmtt.gym.provider.thepeak.process;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.provider.thepeak.pageobjects.ActivityPage;

import java.util.Set;

@Component
public class ActivityPageAction {

    public Set<String> readAllActivities(final WebDriver driver) {
        final ActivityPage activityPage = PageFactory.initElements(driver, ActivityPage.class);
        activityPage.waitForPageToLoad(driver);
        return activityPage.readBookingLinks();
    }

    public void clickOnLink(final WebDriver driver, String linkOnPage) {
        final ActivityPage activityPage = PageFactory.initElements(driver, ActivityPage.class);
        activityPage.waitForPageToLoad(driver);
        activityPage.clickOnLink(driver, linkOnPage);
    }

}
