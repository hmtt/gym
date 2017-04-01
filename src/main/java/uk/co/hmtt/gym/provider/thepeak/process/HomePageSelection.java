package uk.co.hmtt.gym.provider.thepeak.process;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.provider.thepeak.pageobjects.HomePage;

@Component
public class HomePageSelection {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageSelection.class);

    public void makeABooking(final WebDriver driver) {
        final HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.waitForPageToLoad(driver);
        homePage.clickMakeABooking();
    }

    public boolean confirmLoggedIn(final WebDriver driver) {
        final HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        try {
            homePage.waitForPageToLoad(driver);
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not log in", e);
            return false;
        }
    }

}
