package uk.co.hmtt.cukes.core.web.process;

import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.pageobjects.Header;

@Component
public class MobileNavigation {

    @Autowired
    private RuntimeScope runtimeScope;

    public void clickMobileLink(String link) {
        Header header = PageFactory.initElements(runtimeScope.getWebDriver(), Header.class);
        header.waitForPageToBeRead(runtimeScope.getWebDriver());
        header = PageFactory.initElements(runtimeScope.getWebDriver(), Header.class);

        header.clickMobileNavBar();


        if ("home".equals(link)) {
            header.clickHomeLink();
        } else if ("schedule".equals(link)) {
            header.clickScheduleLink();
        } else if ("logout".equals(link)) {
            header.clickLogoutLink();
        }

        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();

    }


}
