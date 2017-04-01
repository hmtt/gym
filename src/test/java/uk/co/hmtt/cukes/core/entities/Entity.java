package uk.co.hmtt.cukes.core.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Entity<T> {

    T mapRow(ResultSet resultSet, int i) throws SQLException;

    String getTableName();

    T persist();

}
