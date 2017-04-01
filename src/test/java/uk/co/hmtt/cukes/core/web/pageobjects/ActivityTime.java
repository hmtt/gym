package uk.co.hmtt.cukes.core.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ActivityTime extends Header {

    @FindBy(className = "activityTime")
    private List<WebElement> activityTimes;

    public void clickActivityTime(String activityTime) {
        for (WebElement activity : activityTimes) {
            if (activity.getText().equals(activityTime)) {
                activity.click();
                break;
            }
        }
    }

}
