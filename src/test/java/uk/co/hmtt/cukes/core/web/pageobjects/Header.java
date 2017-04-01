package uk.co.hmtt.cukes.core.web.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header {

    @FindBy(className = "navbar-toggle")
    private WebElement mobileNavBar;

    @FindBy(id = "navbar-nav")
    private WebElement mobileNavigation;

    @FindBy(className = "navbar-brand")
    private WebElement mobileHomeLink;

    @FindBy(id = "schedule")
    private WebElement mobileScheduleLink;

    @FindBy(id = "logout")
    private WebElement mobileLogoutLink;

    public void clickMobileNavBar() {
        mobileNavBar.click();
    }

    public void clickHomeLink() {
        mobileHomeLink.click();
    }

    public void clickScheduleLink() {
        mobileScheduleLink.click();
    }

    public void clickLogoutLink() {
        mobileLogoutLink.click();
    }

    public void waitForPageToBeRead(final WebDriver driver) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(mobileNavBar));
    }

}
