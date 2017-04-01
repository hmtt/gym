package uk.co.hmtt.gym.provider.thepeak.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityTimePage {

    @FindBy(className = "BookingLinkButton")
    private List<WebElement> boolingLinkButtons;

    public Set<String> readAvailableTimes() {
        final Set<String> availableTimes = new HashSet<>();
        for (WebElement bookingLink : boolingLinkButtons) {
            availableTimes.add(bookingLink.getAttribute("value"));
        }
        return availableTimes;
    }

    public void clickOnTime(final String time) {
        for (WebElement bookingLink : boolingLinkButtons) {
            if (time.equals(bookingLink.getAttribute("value"))) {
                bookingLink.click();
                break;
            }
        }
    }

    public void waitForPageToLoad(final WebDriver webDriver) {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOfAllElements(boolingLinkButtons));
    }

}
