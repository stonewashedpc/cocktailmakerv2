package objects;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import database.boundaries.CocktailBoundary;

/**
 * Cocktail representing an entry in the cocktails table.
 * Also provides methods for retrieving required ingredients, the corresponding
 * CocktailImage and preparation_steps / followup_steps from the database.
 * @author Joel Benseler
 *
 */
public class Cocktail {
	
	private Integer id;
	private String name;
	
	public Cocktail(Integer id, String name) {
		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Amount> findRecipe() throws SQLException {
		return CocktailBoundary.findRecipeById(this.id);
	}
	
	public CocktailImage findImage() throws SQLException {
		return CocktailBoundary.findImageById(this.id);
	}
	
	public List<Step> findPreparationSteps() throws SQLException {
		return CocktailBoundary.findPreparationStepsById(this.id);
	}
	
	public List<Step> findFollowupSteps() throws SQLException {
		return CocktailBoundary.findFollowupStepsById(this.id);
	}
}
