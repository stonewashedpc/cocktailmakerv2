package objects;

import java.util.Objects;

/**
 * Amount representing an entry in the cocktail_ingredient_relation table
 * @author Joel Benseler
 *
 */
public class Amount {
	
	private Ingredient ingredient;
	private Short amount;
	
	public Amount(Ingredient ingredient, Short amount) {
		this.ingredient = Objects.requireNonNull(ingredient);
		this.amount = Objects.requireNonNull(amount);
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public Short getAmount() {
		return amount;
	}

	public void setAmount(Short amount) {
		this.amount = amount;
	}
}
