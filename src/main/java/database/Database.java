package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Interface for database connections.
 * @author Joel Benseler
 *
 */
public interface Database {
	public ResultSet executeQuery(String sql, Optional<InputMapper> inputMapper) throws SQLException;
	public ResultSet executeUpdate(String sql, Optional<InputMapper> inputMapper) throws SQLException;
}
