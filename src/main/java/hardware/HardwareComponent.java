package hardware;

import java.util.Objects;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

/**
 * Abstract class representing hardware-components which can be controlled
 * using a Raspberry Pi's GPIO-Pins
 * @author Joel Benseler
 *
 */
public abstract class HardwareComponent {

	private Byte gpioPin;

	public HardwareComponent(Byte gpioPin) {
		this.gpioPin = Objects.requireNonNull(gpioPin);
	}

	public Byte getGpioPin() {
		return gpioPin;
	}
	
	/**
	 * Sets this HardwareComponent's Pin to high (low, as the CocktailMaker's relay is inverted)
	 * for the specified amount of time (in milliseconds). The executing Thread sleeps for the
	 * duration of the pulse.
	 * @param milliseconds the amount of time to sleep in milliseconds
	 * @throws InterruptedException
	 */
	public void pulse(long milliseconds) throws InterruptedException {
		GpioController gpio = GpioFactory.getInstance();

		GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(GPIOMap.getPin(this.gpioPin), "Pump", PinState.HIGH);

		pin.setShutdownOptions(true, PinState.HIGH);

		pin.low();

		Thread.sleep(milliseconds);

		pin.high();

		gpio.unprovisionPin(pin);
		
		gpio.shutdown();
	}

}
