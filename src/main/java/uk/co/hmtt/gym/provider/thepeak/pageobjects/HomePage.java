package uk.co.hmtt.gym.provider.thepeak.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(partialLinkText = "Make a booking")
    private WebElement makeABooking;

    public void clickMakeABooking() {
        makeABooking.click();
    }

    public void waitForPageToLoad(final WebDriver webDriver) {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.elementToBeClickable(makeABooking));
    }

}
