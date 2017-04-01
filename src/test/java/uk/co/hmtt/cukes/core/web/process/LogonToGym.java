package uk.co.hmtt.cukes.core.web.process;

import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.pageobjects.LogonPage;

@Component
public class LogonToGym {

    @Autowired
    private RuntimeScope runtimeScope;

    public void logon(final uk.co.hmtt.cukes.core.entities.User user) {
        final LogonPage logonPage = PageFactory.initElements(runtimeScope.getWebDriver(), LogonPage.class);
        logonPage.enterUserName(user.getEmail());
        logonPage.enterPassWord(user.getPassCode());
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
        logonPage.submit();
    }

}
