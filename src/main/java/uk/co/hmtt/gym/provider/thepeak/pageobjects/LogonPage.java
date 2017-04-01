package uk.co.hmtt.gym.provider.thepeak.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogonPage {

    private static final int TIME_OUT_IN_SECONDS = 5;

    @FindBy(id = "ctl00_MainContent_InputLogin")
    private WebElement username;

    @FindBy(id = "ctl00_MainContent_InputPassword")
    private WebElement password;

    @FindBy(id = "ctl00_MainContent_btnLogin")
    private WebElement submit;

    public void enterUserName(String clientUserName) {
        this.username.sendKeys(clientUserName);
    }

    public void enterPassWord(String clientPassWord) {
        this.password.sendKeys(clientPassWord);
    }

    public void submit() {
        this.submit.click();
    }

    public void waitForPageToLoad(final WebDriver webDriver) {
        new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOf(username));
        new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOf(password));
        new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeClickable(submit));
    }

}
