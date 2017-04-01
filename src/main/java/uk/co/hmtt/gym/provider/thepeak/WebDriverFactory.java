package uk.co.hmtt.gym.provider.thepeak;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class WebDriverFactory {

    @Value("${webdriver}")
    private String driverType;

    public WebDriver newInstance() {

        final WebDriver driver;
        switch (driverType) {
            case "htmlunit":
                driver = new HtmlUnitDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            default:
                driver = new FirefoxDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return driver;

    }

}
