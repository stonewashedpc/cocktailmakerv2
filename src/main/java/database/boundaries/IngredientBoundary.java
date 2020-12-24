package database.boundaries;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import database.DMLStatement;
import database.DQLStatement;
import objects.Ingredient;

/**
 * Boundary class which provides several methods for retrieving database objects
 * @author Joel Benseler
 *
 */
public class IngredientBoundary {
	
	/*
	 * Data Query Language (DQL) Statements
	 */
	
	public static List<Ingredient> findAll() throws SQLException {
		return new DQLStatement<Ingredient>("SELECT * FROM ingredients", null, RowMap.INGREDIENT_MAPPER).execute();
	}
	
	public static List<Ingredient> findByKeyword(String searchString) throws SQLException {
		return new DQLStatement<Ingredient>("SELECT * FROM ingredients WHERE name LIKE '%?%'", (stmt) -> {
			stmt.setString(1, searchString);
		}, RowMap.INGREDIENT_MAPPER).execute();
	}
	
	public static Ingredient findByID(Integer id) throws SQLException {
		return new DQLStatement<Ingredient>("SELECT * FROM ingredients WHERE id = ?", (stmt) -> {
			stmt.setInt(1, id);
		}, RowMap.INGREDIENT_MAPPER).execute().get(0);
	}
	
	/*
	 * Data Manipulation Language (DML) Statements
	 */
	
	public static Integer insertIngredient(String name, BigDecimal alcoholByVolume) throws SQLException {
		return new DMLStatement("INSERT INTO cocktails VALUES (default, ?, ?) ON DUPLICATE KEY UPDATE alcohol_by_volume = ?", (stmt) -> {
			stmt.setString(1, name);
			stmt.setBigDecimal(2, alcoholByVolume);
			stmt.setBigDecimal(3, alcoholByVolume);
		}).execute().get(0);
	}
	
	public static Integer deleteIngredient(Integer ingredientId) throws SQLException {
		return new DMLStatement("DELETE FROM ingredients WHERE id = ?", (stmt) -> {
			stmt.setInt(1, ingredientId);
		}).execute().get(0);
	}
}
