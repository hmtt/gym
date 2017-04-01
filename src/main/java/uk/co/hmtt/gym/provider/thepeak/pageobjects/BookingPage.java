package uk.co.hmtt.gym.provider.thepeak.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by swilson on 15/11/16.
 */
public class BookingPage {

    @FindBy(id = "ctl00_MainContent_gvClasses")
    private WebElement classesTable;

    @FindBy(className = "button")
    private WebElement button;

    @FindBy(id = "ctl00_MainContent_btnBook")
    private WebElement confirmBooking;

    public String readDate() {
        return classesTable.findElement(By.xpath(".//td[2]")).getText();
    }

    public void clickBook() {
        button.click();
    }

    public void clickConfirmBook() {
        confirmBooking.click();
    }

}
