package database.boundaries;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;

import database.DMLStatement;
import database.DQLStatement;
import objects.Amount;
import objects.Cocktail;
import objects.CocktailImage;

/**
 * Boundary class which provides several methods for retrieving database objects
 * @author Joel Benseler
 *
 */
public class CocktailBoundary {
	
	/*
	 * Data Query Language (DQL) Statements
	 */
	
	public static List<Cocktail> findAll() throws SQLException {
		return new DQLStatement<Cocktail>("SELECT * FROM cocktails", null, RowMap.COCKTAIL_MAPPER).execute();
	}
	
	public static List<Cocktail> findByKeyword(String searchString) throws SQLException {
		return new DQLStatement<Cocktail>("SELECT * FROM cocktails WHERE name LIKE '%?%'", (stmt) -> {
			stmt.setString(1, searchString);
		}, RowMap.COCKTAIL_MAPPER).execute();
	}
	
	public static Cocktail findByID(Integer id) throws SQLException {
		return new DQLStatement<Cocktail>("SELECT * FROM cocktails WHERE id = ?", (stmt) -> {
			stmt.setInt(1, id);
		}, RowMap.COCKTAIL_MAPPER).execute().get(0);
	}
	
	public static List<Cocktail> findPossible() throws SQLException {
		return new DQLStatement<Cocktail>(
				"SELECT DISTINCT cocktail.* FROM cocktails cocktail WHERE NOT EXISTS"
				+ " (SELECT 1 FROM cocktail_ingredient_relation rel WHERE cocktail.id=rel.cocktail_id AND rel.ingredient_id"
				+ " NOT IN (SELECT ingredient_id FROM pumps))", 
				null, RowMap.COCKTAIL_MAPPER).execute();
	}
	
	public static List<Amount> findRecipeById(Integer id) throws SQLException {
		return new DQLStatement<Amount>("SELECT * FROM cocktail_ingredient_relation WHERE cocktail_id = ?", (stmt) -> {
			stmt.setInt(1, id);
		}, RowMap.AMOUNT_MAPPER).execute();
	}
	
	public static CocktailImage findImageById(Integer id) throws SQLException {
		return new DQLStatement<CocktailImage>("SELECT * FROM cocktail_images WHERE cocktail_id = ?", (stmt) -> {
			stmt.setInt(1, id);
		}, RowMap.COCKTAIL_IMAGE_MAPPER).execute().get(0);
	}
	
	/*
	 * Data Manipulation Language (DML) Statements
	 */
	
	public static Integer insert(String name, FileInputStream imageStream) throws SQLException {
		return new DMLStatement("INSERT INTO cocktails VALUES (default, ?, ?)", (stmt) -> {
			stmt.setString(1, name);
			stmt.setBinaryStream(2, imageStream);
		}).execute().get(0);
	}
	
	public static void insertIngredientAmount(Integer cocktailId, Integer ingredientId, Short amount) throws SQLException {
		new DMLStatement("INSERT INTO cocktail_ingredient_relation VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE amount = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
			stmt.setInt(2, ingredientId);
			stmt.setShort(3, amount);
			stmt.setShort(4, amount);
		}).execute();
	}
}
