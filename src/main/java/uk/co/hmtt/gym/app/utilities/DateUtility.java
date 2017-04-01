package uk.co.hmtt.gym.app.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DateUtility {

    private DateUtility() {

    }

    public static boolean isDateExcluded(final String dateToCheck, final List<Calendar> datesToCheckAgainst) throws ParseException {

        final Calendar screenDate = Calendar.getInstance();
        screenDate.setTime(new SimpleDateFormat("E, dd MMMMM yyyy").parse(dateToCheck));

        return datesToCheckAgainst.stream().anyMatch(p -> p.get(Calendar.YEAR) == screenDate.get(Calendar.YEAR) &&
                p.get(Calendar.MONTH) == screenDate.get(Calendar.MONTH) &&
                p.get(Calendar.DAY_OF_MONTH) == screenDate.get(Calendar.DAY_OF_MONTH));

    }

}
