package uk.co.hmtt.gym.app.utilities;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DateUtilityTest {

    private static final String EXCLUDED_DATE = "Monday, 01 February 2016";

    @Test
    public void shouldReportADateAsExcludedIfOnTheExclusionList() throws ParseException {
        final Calendar aDate1 = createCalendar(2016, 2, 1);
        final Calendar aDate2 = createCalendar(2016, 2, 2);
        final List<Calendar> excludedDates = Arrays.asList(new Calendar[]{aDate1, aDate2});
        boolean isDateExcluded = DateUtility.isDateExcluded(EXCLUDED_DATE, excludedDates);
        assertThat(isDateExcluded, is(true));
    }

    @Test
    public void shouldNotReportADateAsExcludedIfNotOnTheExclusionList() throws ParseException {
        final Calendar aDate1 = createCalendar(2016, 2, 2);
        final Calendar aDate2 = createCalendar(2016, 2, 3);
        final List<Calendar> excludedDates = Arrays.asList(new Calendar[]{aDate1, aDate2});
        boolean isDateExcluded = DateUtility.isDateExcluded(EXCLUDED_DATE, excludedDates);
        assertThat(isDateExcluded, is(false));
    }

    private Calendar createCalendar(final int year, final int month, final int dayOfMonth) {
        final Calendar aDate = Calendar.getInstance();
        aDate.set(Calendar.YEAR, year);
        aDate.set(Calendar.MONTH, month - 1);
        aDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return aDate;
    }

}
