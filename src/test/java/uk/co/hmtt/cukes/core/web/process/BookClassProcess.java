package uk.co.hmtt.cukes.core.web.process;

import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.pageobjects.BookClass;

@Component
public class BookClassProcess {

    @Autowired
    private RuntimeScope runtimeScope;

    public void bookClass() {
        BookClass bookClass = PageFactory.initElements(runtimeScope.getWebDriver(), BookClass.class);
        bookClass.clickToAutoEnrol();
        bookClass.submit();
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
    }

    public void addExclusion(final String day, final String month, final String year, boolean useDatePicker) {
        BookClass bookClass = PageFactory.initElements(runtimeScope.getWebDriver(), BookClass.class);
        bookClass.waitForPageToLoad(runtimeScope.getWebDriver());
        bookClass = PageFactory.initElements(runtimeScope.getWebDriver(), BookClass.class);
        if (useDatePicker) {
            bookClass.clickExclusionTextArea();
            bookClass.waitForExclusionWidget(runtimeScope.getWebDriver());
            bookClass.addExclusionDateUsingWidget((CukesWebDriver) runtimeScope.getWebDriver(), day, month, year);
            bookClass.waitForExclusionWidgetToDissapear(runtimeScope.getWebDriver());
        }
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
        bookClass.submit();
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
    }

    public String readHeader() {
        return PageFactory.initElements(runtimeScope.getWebDriver(), BookClass.class).readHeader();
    }

}
