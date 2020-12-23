package objects;

import java.util.Objects;

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
}
