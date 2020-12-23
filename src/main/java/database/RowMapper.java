package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
	public T mapRows(ResultSet rs) throws SQLException;
}
