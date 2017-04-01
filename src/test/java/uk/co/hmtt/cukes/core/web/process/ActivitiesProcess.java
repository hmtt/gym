package uk.co.hmtt.cukes.core.web.process;

import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.pageobjects.Activities;
import uk.co.hmtt.cukes.core.web.pageobjects.Header;

@Component
public class ActivitiesProcess {

    @Autowired
    private RuntimeScope runtimeScope;

    public void selectActivity(final String className) {
        final Header header = PageFactory.initElements(runtimeScope.getWebDriver(), Header.class);
        header.clickHomeLink();
        final Activities activities = PageFactory.initElements(runtimeScope.getWebDriver(), Activities.class);
        activities.clickActivity(className);
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
    }

    public void logOff() {
        final Activities activities = PageFactory.initElements(runtimeScope.getWebDriver(), Activities.class);
        activities.clickLogOut();
    }

}
