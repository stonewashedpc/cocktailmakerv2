package controller;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import objects.Amount;
import objects.Step;

@Named
@ViewScoped
public class CocktailSettingsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cocktailName;
	
	private List<Amount> ingredientAmounts;
	
	private List<Step> preparationSteps;
	
	private List<Step> followupSteps;
	
	private File imageFile;
	
	@PostConstruct
	public void init() {
		
	}

}
