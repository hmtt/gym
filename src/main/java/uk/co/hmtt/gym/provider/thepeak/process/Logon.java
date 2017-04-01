package uk.co.hmtt.gym.provider.thepeak.process;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.provider.thepeak.pageobjects.LogonPage;

@Component
public class Logon {

    public void logon(final WebDriver driver, final User user) {
        final LogonPage logonPage = PageFactory.initElements(driver, LogonPage.class);
        logonPage.waitForPageToLoad(driver);
        logonPage.enterUserName(user.getEmail());
        logonPage.enterPassWord(user.getPasscode());
        logonPage.submit();
    }

    public void logon(final WebDriver driver, final String email, String passcode) {
        final LogonPage logonPage = PageFactory.initElements(driver, LogonPage.class);
        logonPage.waitForPageToLoad(driver);
        logonPage.enterUserName(email);
        logonPage.enterPassWord(passcode);
        logonPage.submit();
    }


}
