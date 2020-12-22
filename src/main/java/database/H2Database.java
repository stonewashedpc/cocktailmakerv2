package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * H2Database connection singleton.
 * @author Joel Benseler
 *
 */
public class H2Database implements Database {
	
	private static H2Database instance;
	
	private static Connection connection;
	
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/testdatenbank";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWORD = "";
	
	/**
	 * Private constructor prevents other classes from instantiating
	 */
	private H2Database() {}
	
	/**
	 * Retrieves an instance of H2Database
	 * @return a H2Database instance
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static H2Database getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new H2Database();
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		}
		return instance;
	}
	
	/**
	 * Executes the given sql query on the database and returns
	 * the generated ResultSet.
	 * @return a ResultSet representing the query's result
	 * @throws SQLException
	 */
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet result = stmt.executeQuery();
		return result;
	}
	
	/**
	 * Executes the given sql query on the database and returns
	 * the any generated keys as a ResultSet.
	 * Used only with SQL Data Manipulation Language (DML) statements.
	 * @return a ResultSet representing the query's result
	 * @throws SQLException
	 */
	@Override
	public ResultSet executeUpdate(String sql) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.executeUpdate();
		return stmt.getGeneratedKeys();
	}

}
