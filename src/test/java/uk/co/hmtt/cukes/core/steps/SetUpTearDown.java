package uk.co.hmtt.cukes.core.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.cukes.configuration.CukesConfig;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriverFactory;
import uk.co.hmtt.cukes.core.entities.*;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;

@ContextConfiguration(classes = {CukesConfig.class})
public class SetUpTearDown {

    @Value("${gymbooker.url}")
    private String gymBookerUrl;

    @Autowired
    private RuntimeScope runtimeScope;

    @Autowired
    private CukesWebDriverFactory webDriverFactory;

    @Autowired
    private EntityDao entityDao;

    @Before
    public void init(Scenario scenario) {
        runtimeScope.clean();
        entityDao.wipe();
        runtimeScope.setScenario(scenario);
        injectEntities();
    }

    @Before(value = {"@web", "~@scenarioConfiguredDriver"})
    public void initWeb(Scenario scenario) {
        final WebDriver webDriver = webDriverFactory.newInstance(scenario);
        runtimeScope.setWebDriver(webDriver);
        webDriver.get(gymBookerUrl);
    }

    @After(value = {"@web"})
    public void tearDownWeb(Scenario scenario) {
        if (runtimeScope.getWebDriver() != null) {
            if (scenario.isFailed()) {
                ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
            }
            runtimeScope.getWebDriver().quit();
        }
    }

    private void injectEntities() {
        User.setEntityDao(entityDao);
        ActivityEntity.setEntityDao(entityDao);
        UserActivityEntity.setEntityDao(entityDao);
        ExclusionEntity.setEntityDao(entityDao);
    }

}
