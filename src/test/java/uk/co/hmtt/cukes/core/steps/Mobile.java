package uk.co.hmtt.cukes.core.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.cukes.configuration.CukesConfig;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriverFactory;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.process.LogonToGym;
import uk.co.hmtt.cukes.core.web.process.MobileNavigation;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = {CukesConfig.class})
public class Mobile {

    public static final String PASS_CODE = "1111";

    @Autowired
    private RuntimeScope runtimeScope;

    @Autowired
    private CukesWebDriverFactory webDriverFactory;

    @Autowired
    private LogonToGym logonToGym;

    @Autowired
    private MobileNavigation mobileNavigation;

    @Value("${gymbooker.url}")
    private String gymBookerUrl;

    @Value("${base.url}")
    private String baseUrl;

    @When("^the user logs onto the mobile site$")
    public void the_user_logs_onto_the_mobile_site() throws Throwable {

        if (runtimeScope.getWebDriver() != null) {
            runtimeScope.getWebDriver().quit();
            runtimeScope.setWebDriver(null);
        }
        final WebDriver webDriver = webDriverFactory.newInstance(runtimeScope.getScenario());

        final Dimension dimension = new Dimension(420, 600);
        webDriver.manage().window().setSize(dimension);

        runtimeScope.setWebDriver(webDriver);
        webDriver.get(gymBookerUrl);

        logonToGym.logon(runtimeScope.getUser());
    }

    @Then("^clicks on <link> and navigates to <url>$")
    public void clicks_on_link_and_navigates_to_url(List<Links> links) throws Throwable {
        for (Links link : links) {
            mobileNavigation.clickMobileLink(link.getLink());
            final String webPage = runtimeScope.getWebDriver().getCurrentUrl().replaceFirst(baseUrl, "");
            assertThat(webPage, is(equalTo(link.getUrl())));
        }
    }

    private class Links {
        private String link;
        private String url;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
