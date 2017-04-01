package uk.co.hmtt.cukes.core.drivers;

import cucumber.api.Scenario;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CukesHtmlUnitDriver extends HtmlUnitDriver implements CukesWebDriver {

    final private static Logger LOGGER = LoggerFactory.getLogger(CukesHtmlUnitDriver.class);

    private Scenario scenario;

    public CukesHtmlUnitDriver(Scenario scenario) {
        super();
        this.scenario = scenario;
    }

    @Override
    public void takeScreenShot() {
        LOGGER.debug("Screenshots not supported in HtmlUnitDriver. Scenario: {}", scenario.getName());
    }

    @Override
    public void maximize() {
        manage().window().maximize();
    }
}
