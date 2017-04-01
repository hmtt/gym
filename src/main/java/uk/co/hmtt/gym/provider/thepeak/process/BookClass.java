package uk.co.hmtt.gym.provider.thepeak.process;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import uk.co.hmtt.gym.provider.thepeak.pageobjects.BookingPage;

@Component
public class BookClass {

    public String readClassDate(final WebDriver webDriver) {
        final BookingPage bookingPage = PageFactory.initElements(webDriver, BookingPage.class);
        return bookingPage.readDate();
    }

    public void book(final WebDriver webDriver) {
        final BookingPage bookingPage = PageFactory.initElements(webDriver, BookingPage.class);
        bookingPage.clickBook();
        final BookingPage bookingConfirmationPage = PageFactory.initElements(webDriver, BookingPage.class);
        bookingConfirmationPage.clickConfirmBook();
    }

}
