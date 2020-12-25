package database.boundaries;

import java.sql.SQLException;
import java.util.List;

import database.DMLStatement;
import database.DQLStatement;
import objects.Pump;

/**
 * Boundary class which provides several methods for retrieving database objects
 * @author Joel Benseler
 *
 */
public class PumpBoundary {
	
	/*
	 * Data Query Language (DQL) Statements
	 */
	
	public static List<Pump> findAll() throws SQLException {
		return new DQLStatement<Pump>("SELECT * FROM pumps", null, RowMap.PUMP_MAPPER).execute();
	}
	
	public static Pump findByGPIO(Byte gpioPin) throws SQLException {
		return new DQLStatement<Pump>("SELECT * FROM pumps WHERE gpio_pin = ?", (stmt) -> {
			stmt.setByte(1, gpioPin);
		}, RowMap.PUMP_MAPPER).execute().get(0);
	}
	
	public static Pump findByIngredientID(Integer ingredientId) throws SQLException {
		return new DQLStatement<Pump>("SELECT * FROM pumps WHERE ingredient_id = ?", (stmt) -> {
			stmt.setInt(1, ingredientId);
		}, RowMap.PUMP_MAPPER).execute().get(0);
	}
	
	/*
	 * Data Manipulation Language (DML) Statements
	 */
	
	public static Integer insertPump(Byte gpioPin, Integer ingredientId, Short flowRate) throws SQLException {
		return new DMLStatement("INSERT INTO pumps VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE ingredient_id = ?, flow_rate = ?", (stmt) -> {
			stmt.setByte(1, gpioPin);
			stmt.setInt(2, ingredientId);
			stmt.setShort(3, flowRate);
			stmt.setInt(4, ingredientId);
			stmt.setShort(5, flowRate);
		}).execute().get(0);
	}
	
	public static void insertPumps(List<Pump> pumps) throws SQLException {
		for (Pump pump : pumps) {
			insertPump(pump.getGpioPin(), pump.getIngredientId(), pump.getFlowRate());
		}
	}
	
	public static void deletePump(Byte gpioPin) throws SQLException {
		new DMLStatement("DELETE FROM pumps WHERE gpio_pin = ?", (stmt) -> {
			stmt.setByte(1, gpioPin);
		}).execute();
	}
	
	public static void deletePumps() throws SQLException {
		new DMLStatement("DELETE FROM pumps", null).execute();
	}
}
