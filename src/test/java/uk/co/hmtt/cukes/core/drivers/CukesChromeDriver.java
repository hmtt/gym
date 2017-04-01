package uk.co.hmtt.cukes.core.drivers;

import cucumber.api.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;

public class CukesChromeDriver extends ChromeDriver implements CukesWebDriver {

    private Scenario scenario;

    public CukesChromeDriver(Scenario scenario) {
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
