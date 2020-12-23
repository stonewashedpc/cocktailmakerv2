package objects;

import java.util.Objects;

/**
 * CocktailImage representing an entry in the cocktail_images table
 * @author Joel Benseler
 *
 */
public class CocktailImage {
	
	private Integer cocktailId;
	private byte[] image;
	
	public CocktailImage(Integer cocktailId, byte[] image) {
		this.cocktailId = Objects.requireNonNull(cocktailId);
		this.image = Objects.requireNonNull(image);
	}

	public Integer getCocktailId() {
		return cocktailId;
	}

	public byte[] getImage() {
		return image;
	}
}
