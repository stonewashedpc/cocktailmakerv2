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
		return new DQLStatement<Ingredient>("SELECT * FROM ingredients WHERE name REGEXP ?", (stmt) -> {
			stmt.setString(1, ".*" + searchString + ".*");
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
		return new DMLStatement("INSERT INTO ingredients VALUES (default, ?, ?)", (stmt) -> {
			stmt.setString(1, name);
			stmt.setBigDecimal(2, alcoholByVolume);
		}).execute().get(0);
	}
	
	public static void updateIngredient(Integer id, String name, BigDecimal alcoholByVolume) throws SQLException {
		new DMLStatement("UPDATE ingredients SET name = ?, alcohol_by_volume = ? WHERE id = ?", (stmt) -> {
			stmt.setString(1, name);
			stmt.setBigDecimal(2, alcoholByVolume);
			stmt.setInt(3, id);
		}).execute();
	}
	
	public static void deleteIngredient(Integer ingredientId) throws SQLException {
		new DMLStatement("DELETE FROM ingredients WHERE id = ?", (stmt) -> {
			stmt.setInt(1, ingredientId);
		}).execute();
	}
}
