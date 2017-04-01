package uk.co.hmtt.cukes.core.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import uk.co.hmtt.cukes.configuration.CukesConfig;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriverFactory;
import uk.co.hmtt.cukes.core.entities.EntityDao;
import uk.co.hmtt.cukes.core.entities.User;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.process.ActivitiesProcess;
import uk.co.hmtt.cukes.core.web.process.LogonToGym;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.co.hmtt.cukes.core.entities.User.buildDefaultAuthorisedUser;

@ContextConfiguration(classes = {CukesConfig.class})
public class Logon extends GymStep {

    public static final String SUCCESSFUL = "successful";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String PASS_CODE = "1111";

    @Autowired
    private RuntimeScope runtimeScope;

    @Autowired
    private LogonToGym logonToGym;

    @Autowired
    private ActivitiesProcess activitiesProcess;

    @Autowired
    private CukesWebDriverFactory webDriverFactory;

    @Value("${gymbooker.url}")
    private String gymBookerUrl;

    @When("^the user logs onto the site with an incorrect pin number$")
    public void the_user_logs_onto_the_site_with_an_incorrect_pin_number() throws Throwable {
        logonToGym.logon(runtimeScope.getUser().withPassCode("2222"));
    }

    @When("^user with email \"([^\"]*)\" logs onto the site$")
    public void user_with_email_logs_onto_the_site(String email) throws Throwable {
        closeWebDriverIfRunningAndStartNewInstance();
        logonToGym.logon(buildDefaultAuthorisedUser().withEmail(email));
    }

    @Then("^authorisation is \"(.*?)\"$")
    public void authorisation_is(String status) throws Throwable {

        printTables(EntityDao.TABLE_NAME.USER, EntityDao.TABLE_NAME.ACTIVITY, EntityDao.TABLE_NAME.USER_ACTIVITY);
        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());

        if (SUCCESSFUL.equals(status)) {
            assertThat(user.getFailedToLogInCount(), is(equalTo(0)));
        } else {
            assertThat(user.getFailedToLogInCount(), is(greaterThan(0)));
        }
    }

    @Then("^the user account is updated to indicate that they are an active user$")
    public void the_user_account_is_updated_to_indicate_that_they_are_an_active_user() throws Throwable {
        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());
        assertThat(user.isEnabled(), is(equalTo(true)));
    }

    @Then("^the user account is updated to reset the failed logon count to \"([^\"]*)\"$")
    public void the_user_account_is_updated_to_reset_the_failed_logon_count_to(String count) throws Throwable {
        final User user = entityDao.getUser(runtimeScope.getUser().getEmail());
        assertThat(user.getFailedToLogInCount(), is(equalTo(Integer.parseInt(count))));
    }

    @And("^clicks the logout link$")
    public void clicks_the_logout_link() throws Throwable {
        activitiesProcess.logOff();
    }

    @Then("^the user cannot access site$")
    public void the_user_cannot_access_site() throws Throwable {
        runtimeScope.getWebDriver().get(gymBookerUrl);
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
        assertThat(runtimeScope.getWebDriver().getCurrentUrl(), containsString("login"));
    }

    private void closeWebDriverIfRunningAndStartNewInstance() {
        if (runtimeScope.getWebDriver() != null) {
            runtimeScope.getWebDriver().quit();
            runtimeScope.setWebDriver(null);
        }
        final WebDriver webDriver = webDriverFactory.newInstance(runtimeScope.getScenario());
        runtimeScope.setWebDriver(webDriver);
        webDriver.get(gymBookerUrl);
    }

}
