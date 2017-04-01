package uk.co.hmtt.cukes.core.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogonPage extends Header {

    @FindBy(name = "user")
    private WebElement userName;

    @FindBy(name = "password")
    private WebElement passWord;

    @FindBy(name = "submit")
    private WebElement submit;

    public void enterUserName(String userName) {
        this.userName.sendKeys(userName);
    }

    public void enterPassWord(String passWord) {
        this.passWord.sendKeys(passWord);
    }

    public void submit() {
        this.userName.submit();
    }

}
