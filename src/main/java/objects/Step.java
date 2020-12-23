package objects;

import java.util.Objects;

public class Step {
	
	private Integer cocktailId;
	private Short stepNr;
	private String title;
	private String description;
	
	public Step(Integer cocktailId, Short stepNr, String title, String description) {
		this.cocktailId = Objects.requireNonNull(cocktailId);
		this.stepNr = Objects.requireNonNull(stepNr);
		this.title = Objects.requireNonNull(title);
		this.description = Objects.requireNonNull(description);
	}

	public Integer getCocktailId() {
		return cocktailId;
	}

	public Short getStepNr() {
		return stepNr;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
