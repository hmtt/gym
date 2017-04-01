package uk.co.hmtt.gym.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uk.co.hmtt.gym.app.annotations.Production;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticator());
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticator());
        super.configure(auth);
    }

    @Bean
    public AuthenticationProvider authenticator() {
        return new GymAuthenticationProvider();
    }

    @Configuration
    @Order(1)
    @Production
    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/service/**")
                    .authorizeRequests()
                    .anyRequest().hasAnyRole("ROLE_USER")
                    .and()
                    .httpBasic();
        }

    }

    @Configuration
    @Order(2)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/gym/**")
                    .access("hasRole('ROLE_USER')")
                    .and()
                    .formLogin().loginPage("/login").failureUrl("/login?error")
                    .usernameParameter("user").passwordParameter("password")
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll();
        }
    }

}
