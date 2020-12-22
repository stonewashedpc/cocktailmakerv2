package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for database connections.
 * @author Joel Benseler
 *
 */
public interface Database {
	public ResultSet executeQuery(String sql) throws SQLException;
	public ResultSet executeUpdate(String sql) throws SQLException;
}
