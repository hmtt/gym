package uk.co.hmtt.gym.config.web;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uk.co.hmtt.gym.app.model.User;
import uk.co.hmtt.gym.app.service.UserService;
import uk.co.hmtt.gym.provider.thepeak.WebDriverFactory;
import uk.co.hmtt.gym.provider.thepeak.process.HomePageSelection;
import uk.co.hmtt.gym.provider.thepeak.process.Logon;

import java.util.Collections;

public class GymAuthenticationProvider implements AuthenticationProvider {

    @Value("${gym.url}")
    private String gymUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private Logon logon;

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Autowired
    private HomePageSelection homePageSelection;

    @Override
    public Authentication authenticate(Authentication authentication) {

        User clientSubmittedDetails = new User();
        clientSubmittedDetails.setEmail((String) authentication.getPrincipal());
        clientSubmittedDetails.setPasscode((String) authentication.getCredentials());

        final User user = userService.getUser((String) authentication.getPrincipal());

        if (user == null || !(user.getPasscode().equals(clientSubmittedDetails.getPasscode()))) {
            if (user != null) {
                userService.updateUserForFailedLogon(user);
            }
            return null;
        }

        final WebDriver driver = webDriverFactory.newInstance();
        driver.get(gymUrl);

        logon.logon(driver, clientSubmittedDetails);
        final boolean successfullyLoggedOn = homePageSelection.confirmLoggedIn(driver);

        driver.quit();

        if (successfullyLoggedOn) {
            user.setPasscode((String) authentication.getCredentials());
            userService.updateUserForSuccessfulLogon(user);
            GrantedAuthority grantedAuths = new SimpleGrantedAuthority("ROLE_USER");
            org.springframework.security.core.userdetails.User appUser = new GymUser((String) authentication.getPrincipal(), (String) authentication.getCredentials(), true, true, true, true, Collections.singleton(grantedAuths), user);
            return new UsernamePasswordAuthenticationToken(appUser, authentication.getCredentials(), Collections.singleton(grantedAuths));
        } else {
            userService.updateUserForFailedLogon(user);
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
