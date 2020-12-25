package objects;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Ingredient representing an entry in the ingredients table
 * @author Joel Benseler
 *
 */
public class Ingredient {
	
	private Integer id;
	private String name;
	private BigDecimal alcoholByVolume;
	
	public Ingredient(Integer id, String name, BigDecimal alcoholByVolume) {
		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
		this.alcoholByVolume = Objects.requireNonNull(alcoholByVolume);
	}

	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getAlcoholByVolume() {
		return alcoholByVolume;
	}
	
	public void setAlcoholByVolume(BigDecimal alcoholByVolume) {
		this.alcoholByVolume = alcoholByVolume;
	}
}
