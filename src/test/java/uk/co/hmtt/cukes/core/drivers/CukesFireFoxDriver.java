package uk.co.hmtt.cukes.core.drivers;

import cucumber.api.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CukesFireFoxDriver extends FirefoxDriver implements CukesWebDriver {

    private Scenario scenario;

    public CukesFireFoxDriver(Scenario scenario) {
        super();
        this.scenario = scenario;
    }

    @Override
    public void takeScreenShot() {
        byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
    }

    @Override
    public void maximize() {
        manage().window().maximize();
    }
}
