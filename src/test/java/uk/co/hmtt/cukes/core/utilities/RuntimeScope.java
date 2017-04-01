package uk.co.hmtt.cukes.core.utilities;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.entities.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class RuntimeScope {

    private Scenario scenario;

    private WebDriver webDriver;

    private Map<String, String> scenarioData = new HashMap<>();

    private Map<String, DataTable> scenarioDataTable = new HashMap<>();

    private int tableCount = 0;

    private User user;

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public int addTable() {
        return tableCount++;
    }

    public void setScenarioData(Map<String, String> scenarioData) {
        this.scenarioData = scenarioData;
    }

    public void setScenarioDataTable(Map<String, DataTable> scenarioDataTable) {
        this.scenarioDataTable = scenarioDataTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clean() {
        setScenario(null);
        setScenarioData(new HashMap<String, String>());
        setScenarioDataTable(new HashMap<String, DataTable>());
        setUser(null);
    }

}
