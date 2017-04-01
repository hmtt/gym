package uk.co.hmtt.cukes.core.web.process;

import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.pageobjects.ActivityTime;

@Component
public class ActivityTimeProcess {

    @Autowired
    private RuntimeScope runtimeScope;

    public void selectActivityTime(final String classTime) {
        ActivityTime activityTime = PageFactory.initElements(runtimeScope.getWebDriver(), ActivityTime.class);
        activityTime.clickActivityTime(classTime);
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
    }

}
