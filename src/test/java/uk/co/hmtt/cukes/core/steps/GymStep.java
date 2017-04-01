package uk.co.hmtt.cukes.core.steps;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.hmtt.cukes.core.entities.EntityDao;
import uk.co.hmtt.cukes.core.model.BookingTable;
import uk.co.hmtt.cukes.core.utilities.CucumberOutput;
import uk.co.hmtt.cukes.core.utilities.RuntimeScope;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Created by swilson on 21/08/16.
 */
public class GymStep {

    @Autowired
    protected RuntimeScope runtimeScope;

    @Autowired
    protected EntityDao entityDao;

    @Autowired
    protected CucumberOutput cucumberOutput;

    protected static final String USER_EMAIL = "USER_EMAIL";

    protected void printTables(EntityDao.TABLE_NAME... tables) {
        runtimeScope.getScenario().write("<b>Database Snapshot</b><br/>");
        for (EntityDao.TABLE_NAME table : tables) {
            cucumberOutput.writeTable(table, entityDao.fetchTable(table));
        }
    }

    protected void compareLists(List<BookingTable> expected, List<BookingTable> actual) {
        final List<String> notFound = new ArrayList<>();
        for (BookingTable expectedExclusion : expected) {
            if (!actual.contains(expectedExclusion)) {
                notFound.add(expectedExclusion.toString());
            }
        }
        if (notFound.size() > 0) {
            throw new AssertionError(format("Could not find the following expectations: \n\n%s", join(notFound, "\n")));
        }
    }

}
