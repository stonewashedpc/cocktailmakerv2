package database.boundaries;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;

import database.DMLStatement;
import database.DQLStatement;
import objects.Amount;
import objects.Cocktail;
import objects.CocktailImage;
import objects.Step;

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
	
	public static List<Step> findPreparationStepsById(Integer id) throws SQLException {
		return new DQLStatement<Step>("SELECT * FROM preparation_steps WHERE cocktail_id = ? ORDER BY step_nr ASC", (stmt) -> {
			stmt.setInt(1, id);
		}, RowMap.COCKTAIL_STEP_MAPPER).execute();
	}
	
	public static List<Step> findFollowupStepsById(Integer id) throws SQLException {
		return new DQLStatement<Step>("SELECT * FROM followup_steps WHERE cocktail_id = ? ORDER BY step_nr ASC", (stmt) -> {
			stmt.setInt(1, id);
		}, RowMap.COCKTAIL_STEP_MAPPER).execute();
	}
	
	/*
	 * Data Manipulation Language (DML) Statements
	 */
	
	public static Integer insertCocktail(String name) throws SQLException {
		return new DMLStatement("INSERT INTO cocktails VALUES (default, ?)", (stmt) -> {
			stmt.setString(1, name);
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
	
	public static void insertIngredientAmounts(Integer cocktailId, List<Amount> amounts) throws SQLException {
		for (Amount amount : amounts) {
			insertIngredientAmount(cocktailId, amount.getIngredientId(), amount.getAmount());
		}
	}
	
	public static void deleteIngredientAmounts(Integer cocktailId) throws SQLException {
		new DMLStatement("DELETE FROM cocktail_ingredient_relation WHERE cocktail_id = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
		}).execute();
	}
	
	public static void insertCocktailImage(Integer cocktailId, FileInputStream fileInputStream) throws SQLException {
		new DMLStatement("INSERT INTO cocktail_images VALUES (?, ?) ON DUPLICATE KEY UPDATE display_image = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
			stmt.setBinaryStream(2, fileInputStream);
			stmt.setBinaryStream(3, fileInputStream);
		}).execute();
	}
	
	public static void deleteCocktailImage(Integer cocktailId) throws SQLException {
		new DMLStatement("DELETE FROM cocktail_images WHERE cocktail_id = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
		}).execute();
	}
	
	public static void insertPreparationStep(Integer cocktailId, Short stepNr, String title, String description) throws SQLException {
		new DMLStatement("INSERT INTO preparation_steps VALUES (default, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title = ?, description = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
			stmt.setShort(2, stepNr);
			stmt.setString(3, title);
			stmt.setString(4, description);
			stmt.setString(5, title);
			stmt.setString(6, description);
		}).execute();
	}
	
	public static void insertPreparationSteps(List<Step> steps) throws SQLException {
		for (Step step : steps) {
			insertPreparationStep(step.getCocktailId(), step.getStepNr(), step.getTitle(), step.getDescription());
		}
	}
	
	public static void deletePreparationSteps(Integer cocktailId) throws SQLException {
		new DMLStatement("DELETE FROM preparation_steps WHERE cocktail_id = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
		}).execute();
	}
	
	public static void insertFollowupStep(Integer cocktailId, Short stepNr, String title, String description) throws SQLException {
		new DMLStatement("INSERT INTO followup_steps VALUES (default, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title = ?, description = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
			stmt.setShort(2, stepNr);
			stmt.setString(3, title);
			stmt.setString(4, description);
			stmt.setString(5, title);
			stmt.setString(6, description);
		}).execute();
	}
	
	public static void insertFollowupSteps(List<Step> steps) throws SQLException {
		for (Step step : steps) {
			insertFollowupStep(step.getCocktailId(), step.getStepNr(), step.getTitle(), step.getDescription());
		}
	}
	
	public static void deleteFollowupSteps(Integer cocktailId) throws SQLException {
		new DMLStatement("DELETE FROM followup_steps WHERE cocktail_id = ?", (stmt) -> {
			stmt.setInt(1, cocktailId);
		}).execute();
	}
}
