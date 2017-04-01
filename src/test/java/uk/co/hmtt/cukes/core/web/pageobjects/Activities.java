package uk.co.hmtt.cukes.core.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Activities extends Header {

    @FindBy(id = "schedule")
    private WebElement scheduleLink;

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(tagName = "a")
    private List<WebElement> activities;

    @FindBy(xpath = "//span[contains(@id, 'scheduledBooking_')]")
    private List<WebElement> sheduledActivities;

    @FindBy(xpath = "//span[contains(@id, 'exclude_')]")
    private List<WebElement> exclusions;

    public void clickLogOut() {
        logout.click();
    }

    public void clickActivity(String activityName) {
        for (WebElement activity : activities) {
            if (activity.getText().equals(activityName)) {
                activity.click();
                break;
            }
        }
    }

}
