package uk.co.hmtt.cukes.core.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends Header {

    @FindBy(xpath = "//span[contains(@id, 'scheduledBookingDate_')]")
    private List<WebElement> sheduledActivitiesDate;

    @FindBy(xpath = "//span[contains(@id, 'scheduledBookingName_')]")
    private List<WebElement> sheduledActivitiesName;

    @FindBy(xpath = "//span[contains(@id, 'scheduledBookingExclusionName_')]")
    private List<WebElement> exclusionName;

    @FindBy(xpath = "//span[contains(@id, 'scheduledBookingExclusionDate_')]")
    private List<WebElement> exclusionTime;

    @FindBy(xpath = "//span[contains(@id, 'scheduledBookingExclusionSpecifiedDate_')]")
    private List<WebElement> exclusionRequestedDate;

    public List<ScheduledActivity> readSchedule() {
        final List<ScheduledActivity> schedule = new ArrayList<>();
        for (int i = 0; i < sheduledActivitiesName.size(); i++) {
            final ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setClassName(sheduledActivitiesName.get(i).getText());
            scheduledActivity.setClassTime(sheduledActivitiesDate.get(i));
            schedule.add(scheduledActivity);
        }
        return schedule;
    }

    public List<Exclusion> readExclusions() {
        final List<Exclusion> exclusions = new ArrayList<>();
        for (int i = 0; i < exclusionName.size(); i++) {
            final Exclusion exclusion = new Exclusion();
            exclusion.setClassName(exclusionName.get(i).getText());
            exclusion.setClassTime(exclusionTime.get(i));
            exclusion.setRequestedExclusion(exclusionRequestedDate.get(i).getText());
            exclusions.add(exclusion);
        }
        return exclusions;
    }

    public class ScheduledActivity {
        private String className;
        private WebElement classTime;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getClassTime() {
            return classTime.getText();
        }

        public void setClassTime(WebElement classTime) {
            this.classTime = classTime;
        }

        public void navigateToBooking() {
            classTime.click();
        }

    }

    public class Exclusion extends ScheduledActivity {

        private String requestedExclusion;

        public String getRequestedExclusion() {
            return requestedExclusion;
        }

        public void setRequestedExclusion(String requestedExclusion) {
            this.requestedExclusion = requestedExclusion;
        }
    }

}
