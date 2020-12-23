package objects;

import java.util.Objects;

/**
 * Amount representing an entry in the cocktail_ingredient_relation table
 * @author Joel Benseler
 *
 */
public class Amount {
	
	private Integer ingredientId;
	private Short amount;
	
	public Amount(Integer ingredientId, Short amount) {
		this.ingredientId = Objects.requireNonNull(ingredientId);
		this.amount = Objects.requireNonNull(amount);
	}

	public Integer getIngredientId() {
		return ingredientId;
	}

	public Short getAmount() {
		return amount;
	}
}
