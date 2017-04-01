package uk.co.hmtt.cukes.core.web.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.hmtt.cukes.core.drivers.CukesWebDriver;
import uk.co.hmtt.cukes.core.exceptions.GymTestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static uk.co.hmtt.gym.app.utilities.CollectionsUtil.safeIterator;

public class BookClass extends Header {

    @FindBy(name = "booked")
    private WebElement booked;

    @FindBy(id = "exclusionDate")
    private WebElement exclusionDate;

    @FindBy(id = "submit")
    private WebElement submit;

    @FindBy(id = "ui-datepicker-div")
    private WebElement datePickerWidget;

    @FindBy(linkText = "[home]")
    private WebElement homeLink;

    @FindBy(id = "exclusionHeader")
    private WebElement exclusionHeader;

    @FindBy(id = "classNameAndDate")
    private WebElement header;

    public void clickToAutoEnrol() {
        booked.click();
    }

    public void submit() {
        submit.click();
    }

    public void clickExclusionTextArea() {
        exclusionDate.click();
    }

    public void addExclusionDateUsingWidget(final CukesWebDriver webDriver, String dayOfMonth, String month, String year) {
        selectYearOnWidget((WebDriver) webDriver, year);
        selectMonthOnWidget((WebDriver) webDriver, month);
        selectDayOnWidget((WebDriver) webDriver, dayOfMonth);
    }

    private void selectMonthOnWidget(WebDriver webDriver, String month) {
        final WebElement datePickerMonth = webDriver.findElement(By.className("ui-datepicker-month"));
        int monthAsNumber = getMonthAsNumber(datePickerMonth.getText());
        final int requiredMonth = new Integer(month);

        if (requiredMonth > monthAsNumber) {
            while (requiredMonth > monthAsNumber) {
                webDriver.findElement(By.linkText("Next")).click();
                monthAsNumber = getMonthAsNumber(webDriver.findElement(By.className("ui-datepicker-month")).getText());
            }
        }

        if (requiredMonth < monthAsNumber) {
            while (requiredMonth < monthAsNumber) {
                webDriver.findElement(By.linkText("Prev")).click();
                monthAsNumber = getMonthAsNumber(webDriver.findElement(By.className("ui-datepicker-month")).getText());
            }
        }
    }

    private int getMonthAsNumber(final String month) {
        try {
            Date date = new SimpleDateFormat("MMMMM", Locale.ENGLISH).parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH) + 1;
        } catch (ParseException e) {
            throw new GymTestException(e);
        }
    }

    private void selectYearOnWidget(WebDriver webDriver, String year) {
        final WebElement element = webDriver.findElement(By.className("ui-datepicker-year"));

        Integer datePickerYear = new Integer(element.getText());
        final Integer requiredYear = new Integer(year);

        if (requiredYear > datePickerYear) {
            while (requiredYear > datePickerYear) {
                webDriver.findElement(By.xpath("//a[@title='Next']")).click();
                datePickerYear = new Integer(webDriver.findElement(By.className("ui-datepicker-year")).getText());
            }
        }
    }

    private void selectDayOnWidget(WebDriver webDriver, String dayOfMonth) {
        final List<WebElement> elements = webDriver.findElements(By.className("ui-state-default"));
        final int dayOfMonthRequired = Integer.parseInt(dayOfMonth);
        for (WebElement element : safeIterator(elements)) {
            int day = Integer.parseInt(element.getText());
            if (day == dayOfMonthRequired) {
                element.click();
                return;
            }
        }
    }

    public String readHeader() {
        return header.getText();
    }

    public void waitForPageToLoad(final WebDriver webDriver) {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.visibilityOf(submit));
    }

    public void waitForExclusionWidget(final WebDriver webDriver) {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.id("ui-datepicker-div")));
    }

    public void waitForExclusionWidgetToDissapear(final WebDriver webDriver) {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.invisibilityOfElementLocated(By.id("ui-datepicker-div")));
    }

}
