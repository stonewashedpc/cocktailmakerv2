package database.boundaries;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.RowMapper;
import objects.Amount;
import objects.Cocktail;
import objects.CocktailImage;
import objects.Ingredient;
import objects.Pump;
import objects.Step;

/**
 * Provides several RowMapper constants for mapping database ResultSets to objects
 * @author Joel Benseler
 *
 */
public class RowMap {

	private RowMap() {
	}

	public static final RowMapper<Cocktail> COCKTAIL_MAPPER = new RowMapper<Cocktail>() {

		@Override
		public Cocktail mapRows(ResultSet rs) throws SQLException {
			Integer id = rs.getInt("id");
			String name = rs.getString("name");
			return new Cocktail(id, name);
		}
	};

	public static final RowMapper<Ingredient> INGREDIENT_MAPPER = new RowMapper<Ingredient>() {

		@Override
		public Ingredient mapRows(ResultSet rs) throws SQLException {
			Integer id = rs.getInt("id");
			String name = rs.getString("name");
			BigDecimal alcoholByVolume = rs.getBigDecimal("alcohol_by_volume");
			return new Ingredient(id, name, alcoholByVolume);
		}
	};

	public static final RowMapper<Pump> PUMP_MAPPER = new RowMapper<Pump>() {

		@Override
		public Pump mapRows(ResultSet rs) throws SQLException {
			Byte gpioPin = rs.getByte("gpio_pin");
			Integer ingredientId = rs.getInt("ingredient_id");
			Short flowRate = rs.getShort("flow_rate");
			return new Pump(gpioPin, ingredientId, flowRate);
		}
	};
	
	public static final RowMapper<Amount> AMOUNT_MAPPER = new RowMapper<Amount>() {

		@Override
		public Amount mapRows(ResultSet rs) throws SQLException {
			Integer id = rs.getInt("ingredient_id");
			String name = rs.getString("name");
			BigDecimal alcoholByVolume = rs.getBigDecimal("alcohol_by_volume");
			Short amount = rs.getShort("amount");
			return new Amount(new Ingredient(id, name, alcoholByVolume), amount);
		}
	};
	
	public static final RowMapper<CocktailImage> COCKTAIL_IMAGE_MAPPER = new RowMapper<CocktailImage>() {

		@Override
		public CocktailImage mapRows(ResultSet rs) throws SQLException {
			Integer id = rs.getInt("id");
			Blob imageBlob = rs.getBlob("display_image");
			byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
			imageBlob.free();
			return new CocktailImage(id, imageBytes);
		}
	};
	
	public static final RowMapper<Step> COCKTAIL_STEP_MAPPER = new RowMapper<Step>() {

		@Override
		public Step mapRows(ResultSet rs) throws SQLException {
			Integer cocktailId = rs.getInt("cocktail_id");
			Short stepNr = rs.getShort("step_nr");
			String title = rs.getString("title");
			String description = rs.getString("description");
			return new Step(cocktailId, stepNr, title, description);
		}
	};

}
