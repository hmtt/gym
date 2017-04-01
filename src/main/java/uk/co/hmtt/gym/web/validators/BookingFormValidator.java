package uk.co.hmtt.gym.web.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.co.hmtt.gym.app.model.BookingForm;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class BookingFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clz) {
        return true;
    }

    @Override
    public void validate(Object obj, Errors errors) {

        if (!(obj instanceof BookingForm)) {
            return;
        }

        final BookingForm bookingForm = (BookingForm) obj;
        if (StringUtils.isNotEmpty(bookingForm.getExclusionDate())) {
            try {
                new SimpleDateFormat("dd/MM/yyyy").parse(bookingForm.getExclusionDate());
            } catch (ParseException e) {
                errors.rejectValue("exclusionDate", "bookingform.format.date", "date in incorrect format");
            }
        }
        if (!bookingForm.isBooked() && StringUtils.isNotEmpty(bookingForm.getExclusionDate())) {
            errors.rejectValue("exclusionDate", "bookingform.nobooking ", "Auto enrol in class before adding exclusion");
        }
    }
}
