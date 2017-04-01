package uk.co.hmtt.cukes.core.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import uk.co.hmtt.cukes.core.entities.EntityDao;

@Component
public class CucumberOutput {

    @Autowired
    private RuntimeScope runtimeScope;

    public void writeTable(EntityDao.TABLE_NAME name, SqlRowSet sqlRowSet) {

        final int table = runtimeScope.addTable();

        final String[] columnNames = sqlRowSet.getMetaData().getColumnNames();

        final StringBuilder dataTable = new StringBuilder("<b>" + name.toString() + " Table</b> ");

        if (table == 1) {
            dataTable.append("<style>\n" +
                    ".cukes_table td { \n" +
                    "    padding: 5px;\n" +
                    "}\n" +
                    "</style>");
        }

        dataTable.append("<a href=\"\" onclick=\"table=document.getElementById('table_").append(table).append("'); ").append("table.style.display = (table.style.display == 'none' ? 'block' : 'none');return false\">(+)</a>");

        dataTable.append("<br/><table class='cukes_table' id='table_").append(table).append("' style='display:none'>");
        dataTable.append("<tr>");
        for (String column : columnNames) {
            dataTable.append("<td>");
            dataTable.append(column);
            dataTable.append("</td>");
        }
        dataTable.append("</tr>");

        while (sqlRowSet.next()) {
            dataTable.append("<tr>");
            for (String column : columnNames) {
                dataTable.append("<td>");
                dataTable.append(sqlRowSet.getObject(column));
                dataTable.append("</td>");
            }
            dataTable.append("</tr>");
        }
        dataTable.append("</table>");

        runtimeScope.getScenario().write(dataTable.toString());

    }

}
