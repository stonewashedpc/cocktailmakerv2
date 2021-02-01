package controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import objects.Ingredient;

@Named
@FacesConverter(value = "ingredientConverter", managed = true)
public class IngredientConverter implements Converter<Ingredient> {

	@Override
	public Ingredient getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			CocktailSettingsController cocktailSettingsController = context.getApplication().evaluateExpressionGet(context,
					"#{cocktailSettingsController}", CocktailSettingsController.class);
			return cocktailSettingsController.getIngredients().stream().filter(i -> i.getId() == Integer.valueOf(value))
					.findFirst().get();
		} else return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Ingredient value) {
		if (value != null) {
			Ingredient ingredient = (Ingredient) value;
			return ingredient.getId().toString();
		} else return null;
	}

}
