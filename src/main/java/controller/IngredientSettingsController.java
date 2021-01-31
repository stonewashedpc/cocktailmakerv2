package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import database.boundaries.IngredientBoundary;
import objects.Ingredient;

@Named
@ViewScoped
public class IngredientSettingsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ingredientName;

	private BigDecimal alcoholByVolume;
	
	private List<Ingredient> ingredients;

	@PostConstruct
	public void init() {
		fillDataTable();
	}

	public void showMessage(Severity severity, String title, String description) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msg", new FacesMessage(severity, title, description));
	}

	public void fillDataTable() {
		try {
			this.ingredients = IngredientBoundary.findAll();
		} catch (SQLException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("growlEdit", new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB-Zugriff fehlgeschlagen",
					"Die Zutaten konnten nicht abgerufen werden!"));
			e.printStackTrace();
		}
	}

	public void addIngredient() {
		try {
			IngredientBoundary.insertIngredient(this.ingredientName, this.alcoholByVolume);
			showMessage(FacesMessage.SEVERITY_INFO, "Hinzufügen erfolgreich",
					"Die Zutat \"" + this.ingredientName + "\" wurde hinzugefügt!");
			fillDataTable();
			this.ingredientName = "";
			this.alcoholByVolume = new BigDecimal(0);
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Hinzufügen fehlgeschlagen",
					"Die Zutat \"" + this.ingredientName + "\" existiert bereits!");
			e.printStackTrace();
		}
	}

	public void onRowEdit(RowEditEvent<Ingredient> event) {
		Ingredient updated = event.getObject();
		try {
			IngredientBoundary.updateIngredient(updated.getId(), updated.getName(), updated.getAlcoholByVolume());
			showMessage(FacesMessage.SEVERITY_INFO, "Bearbeiten erfolgreich",
					"Die Zutat \"" + updated.getName() + "\" wurde aktualisiert!");
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Bearbeiten fehlgeschlagen",
					"Die Zutat \"" + updated.getName() + "\" konnte nicht aktualisiert werden!");
			fillDataTable(); // Reload table to revert changes
			e.printStackTrace();
		}
	}
	
	public void onRowDelete(Ingredient ingredient) {
		try {
			IngredientBoundary.deleteIngredient(ingredient.getId());
			this.ingredients.remove(ingredient);
			showMessage(FacesMessage.SEVERITY_INFO, "Entfernen erfolgreich",
					"Die Zutat \"" + ingredient.getName() + "\" wurde entfernt!");
		} catch (SQLException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Entfernen fehlgeschlagen",
					"Die Zutat \"" + ingredient.getName() + "\" konnte nicht entfernt werden!");
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent<Ingredient> event) {

	}

	public BigDecimal getAlcoholByVolume() {
		return alcoholByVolume;
	}

	public void setAlcoholByVolume(BigDecimal alcoholByVolume) {
		this.alcoholByVolume = alcoholByVolume;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
}
