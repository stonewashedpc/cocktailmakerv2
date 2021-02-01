package controller;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import database.boundaries.CocktailBoundary;
import database.boundaries.IngredientBoundary;
import objects.Amount;
import objects.Cocktail;
import objects.Ingredient;
import objects.Step;

@Named
@ViewScoped
public class CocktailSettingsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String newCocktailName;

	private List<Cocktail> cocktails;

	// Basedata

	private Integer cocktailId;

	private String cocktailName;

	// IngredientAmounts

	private Ingredient ingredient;

	private Short amount;

	private List<Ingredient> ingredients;

	private List<Amount> ingredientAmounts;

	// PreparationSteps

	private String preparationStepText;

	private List<Step> preparationSteps;

	// FollowupSteps

	private String followupStepText;

	private List<Step> followupSteps;

	// Image

	private File imageFile;

	@PostConstruct
	public void init() {
		fillDataTable();
		try {
			this.ingredients = IngredientBoundary.findAll();
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "DB-Zugriff fehlgeschlagen",
					"Verfügbare Zutaten konnten nicht abgerufen werden!");
			e.printStackTrace();
		}
	}

	public void showMessage(Severity severity, String title, String description) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msg", new FacesMessage(severity, title, description));
	}

	public void addCocktail() {
		try {
			Integer generatedId = CocktailBoundary.insertCocktail(this.newCocktailName);
			showMessage(FacesMessage.SEVERITY_INFO, "Hinzufügen erfolgreich",
					"Der Cocktail \"" + this.newCocktailName + "\" wurde hinzugefügt!");
			fillDataTable();
			this.newCocktailName = "";
			editCocktail(CocktailBoundary.findByID(generatedId));
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Hinzufügen fehlgeschlagen",
					"Der Cocktail \"" + this.newCocktailName + "\" existiert bereits!");
			e.printStackTrace();
		}
	}

	public void editCocktail(Cocktail cocktail) {
		this.cocktailId = cocktail.getId();
		this.cocktailName = cocktail.getName();
		try {
			this.ingredientAmounts = cocktail.findRecipe();
			this.preparationSteps = cocktail.findPreparationSteps();
			this.followupSteps = cocktail.findFollowupSteps();
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "DB-Zugriff fehlgeschlagen",
					"Die Cocktail-Daten konnten nicht abgerufen werden!");
			e.printStackTrace();
		}
		PrimeFaces.current().executeScript("PF('dlg3').show()");
	}

	private void fillDataTable() {
		try {
			this.setCocktails(CocktailBoundary.findAll());
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "DB-Zugriff fehlgeschlagen",
					"Die Cocktails konnten nicht abgerufen werden!");
			e.printStackTrace();
		}
	}

	public void onRowEdit(Cocktail cocktail) {
		editCocktail(cocktail);
	}

	public void onRowDelete(Cocktail cocktail) {
		try {
			CocktailBoundary.deleteCocktail(cocktail.getId());
			this.cocktails.remove(cocktail);
			showMessage(FacesMessage.SEVERITY_INFO, "Entfernen erfolgreich",
					"Der Cocktail \"" + cocktail.getName() + "\" wurde entfernt!");
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Entfernen fehlgeschlagen",
					"Der Cocktail \"" + cocktail.getName() + "\" konnte nicht entfernt werden!");
			e.printStackTrace();
		}
	}

	public void addIngredientAmount() {
		if (this.ingredient != null && this.amount != null && this.amount >= 0) {
			if (!this.ingredientAmounts.stream().anyMatch(i -> i.getIngredient().getId() == this.ingredient.getId())) {
				this.ingredientAmounts.add(new Amount(this.ingredient, this.amount));
			} else
				showMessage(FacesMessage.SEVERITY_ERROR, "Hinzufügen fehlgeschlagen",
						"Zutat wurde bereits hinzugefügt!");
		} else
			showMessage(FacesMessage.SEVERITY_ERROR, "Hinzufügen fehlgeschlagen",
					"Bitte zunächst Zutat und Menge auswählen!");
	}

	public void onAmountRowEdit(RowEditEvent<Amount> event) {

	}

	public void onAmountRowDelete(Amount ingredientAmount) {
		this.ingredientAmounts.remove(ingredientAmount);
	}

	public void onAmountRowCancel(RowEditEvent<Amount> event) {

	}

	public void onCocktailSave() {
		try {
			CocktailBoundary.updateCocktail(this.cocktailId, this.cocktailName);
			CocktailBoundary.deleteIngredientAmounts(this.cocktailId);
			CocktailBoundary.insertIngredientAmounts(this.cocktailId, this.ingredientAmounts);
			this.cocktails.stream().filter(c -> c.getId() == this.cocktailId).findFirst().get().setName(this.cocktailName);
			PrimeFaces.current().executeScript("PF('dlg3').hide()");
			showMessage(FacesMessage.SEVERITY_INFO, "Speichern erfolgreich",
					"Der Cocktail \"" + this.cocktailName + "\" wurde erfolgreich gespeichert!");
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Speichern fehlgeschlagen",
					"Während dem Datenbankzugriff ist ein Fehler aufgetreten!");
			e.printStackTrace();
		}
	}

	public Integer getCocktailId() {
		return cocktailId;
	}

	public void setCocktailId(Integer cocktailId) {
		this.cocktailId = cocktailId;
	}

	public String getCocktailName() {
		return cocktailName;
	}

	public void setCocktailName(String cocktailName) {
		this.cocktailName = cocktailName;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Short getAmount() {
		return amount;
	}

	public void setAmount(Short amount) {
		this.amount = amount;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Amount> getIngredientAmounts() {
		return ingredientAmounts;
	}

	public void setIngredientAmounts(List<Amount> ingredientAmounts) {
		this.ingredientAmounts = ingredientAmounts;
	}

	public String getPreparationStepText() {
		return preparationStepText;
	}

	public void setPreparationStepText(String preparationStepText) {
		this.preparationStepText = preparationStepText;
	}

	public List<Step> getPreparationSteps() {
		return preparationSteps;
	}

	public void setPreparationSteps(List<Step> preparationSteps) {
		this.preparationSteps = preparationSteps;
	}

	public String getFollowupStepText() {
		return followupStepText;
	}

	public void setFollowupStepText(String followupStepText) {
		this.followupStepText = followupStepText;
	}

	public List<Step> getFollowupSteps() {
		return followupSteps;
	}

	public void setFollowupSteps(List<Step> followupSteps) {
		this.followupSteps = followupSteps;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getNewCocktailName() {
		return newCocktailName;
	}

	public void setNewCocktailName(String newCocktailName) {
		this.newCocktailName = newCocktailName;
	}

	public List<Cocktail> getCocktails() {
		return cocktails;
	}

	public void setCocktails(List<Cocktail> cocktails) {
		this.cocktails = cocktails;
	}
}
