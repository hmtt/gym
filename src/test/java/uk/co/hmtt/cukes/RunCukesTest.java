package uk.co.hmtt.cukes;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json", "html:target/report"},
        features = {"classpath:features"},
        glue = {"uk.co.hmtt.cukes.core.steps"},
        tags = {"~@wip"},
        monochrome = true)
public class RunCukesTest {

}
