package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface InputMapper {
	public void mapInput(PreparedStatement stmt) throws SQLException;
}
