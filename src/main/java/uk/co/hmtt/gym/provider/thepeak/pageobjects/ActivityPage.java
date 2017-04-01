package uk.co.hmtt.gym.provider.thepeak.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityPage.class);

    @FindBy(className = "BookingLinkButton")
    private List<WebElement> bookingLinks;

    public Set<String> readBookingLinks() {
        final Set<String> availableActivities = new HashSet<>();
        for (WebElement bookingLink : bookingLinks) {
            availableActivities.add(bookingLink.getAttribute("value"));
        }
        return availableActivities;
    }

    public void clickOnLink(final WebDriver webDriver, String linkName) {
        LOGGER.debug("clicking on link {}", linkName);
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@value,'" + linkName + "')]")));
        webDriver.findElement(By.xpath("//input[contains(@value,'" + linkName + "')]")).click();

    }

    public void waitForPageToLoad(final WebDriver webDriver) {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOfAllElements(bookingLinks));
    }

}
