package uk.co.hmtt.cukes.core.web.process;

import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;
import uk.co.hmtt.cukes.core.web.pageobjects.Schedule;

import java.util.List;

@Component
public class ScheduleProcess {

    @Autowired
    private RuntimeScope runtimeScope;

    public List<Schedule.Exclusion> readExclusions() {
        navigateToScheduleScreen();
        final Schedule schedule = PageFactory.initElements(runtimeScope.getWebDriver(), Schedule.class);
        return schedule.readExclusions();
    }

    public List<Schedule.ScheduledActivity> readBookings() {
        navigateToScheduleScreen();
        final Schedule schedule = PageFactory.initElements(runtimeScope.getWebDriver(), Schedule.class);
        return schedule.readSchedule();
    }

    public void navigateToScheduleScreen() {
        final Schedule schedule = PageFactory.initElements(runtimeScope.getWebDriver(), Schedule.class);
        schedule.clickScheduleLink();
        ((CukesWebDriver) runtimeScope.getWebDriver()).takeScreenShot();
    }

}
