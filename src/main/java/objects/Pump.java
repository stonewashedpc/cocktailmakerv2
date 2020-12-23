package objects;

import java.util.Objects;

import hardware.HardwareComponent;

/**
 * Pump representing an entry in the pumps table
 * @author Joel Benseler
 *
 */
public class Pump extends HardwareComponent {
	
	private Integer ingredientId;
	private Short flowRate;
	
	public Pump(Byte gpioPin, Integer ingredientId, Short flowRate) {
		super(gpioPin);
		this.ingredientId = Objects.requireNonNull(ingredientId);
		this.flowRate = Objects.requireNonNull(flowRate);
	}

	public Integer getIngredientId() {
		return ingredientId;
	}

	public Short getFlowRate() {
		return flowRate;
	}
	
	/**
	 * Sets this pump's GPIO-Pin to high (low) for as long as it needs to
	 * pump the spedified amount of liquid. The executing Thread sleeps for the
	 * amount of time the pump is active.
	 * @param milliliters the amount to pump in milliliters
	 * @throws InterruptedException
	 */
	public void pumpAmount(Short milliliters) throws InterruptedException {
		this.pulse((long)((float) milliliters * 60000 / (float) this.flowRate));
	}
}
