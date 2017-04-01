package uk.co.hmtt.cukes.core.drivers;

import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CukesWebDriverFactory {

    public static final String FIREFOX = "firefox";
    public static final String CHROME = "chrome";

    @Value("${test.webdriver}")
    private String driverType;

    public WebDriver newInstance(Scenario scenario) {

        final WebDriver driver;
        switch (driverType) {
            case FIREFOX:
                driver = new CukesFireFoxDriver(scenario);
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/linux_x64/chromedriver");
                driver = new CukesChromeDriver(scenario);
                break;
            default:
                driver = new CukesHtmlUnitDriver(scenario);
                break;
        }

        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

        return driver;

    }

}
