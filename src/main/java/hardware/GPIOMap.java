package hardware;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Maps Integer values to the corresponding Pin object
 * @author Joel Benseler
 *
 */
public class GPIOMap {

	private static final Map<Byte, Pin> gpio_map = new HashMap<Byte, Pin>();
	
	private GPIOMap() {}

	static {
		gpio_map.put((byte) 0, RaspiPin.GPIO_00);
		gpio_map.put((byte) 1, RaspiPin.GPIO_01);
		gpio_map.put((byte) 2, RaspiPin.GPIO_02);
		gpio_map.put((byte) 3, RaspiPin.GPIO_03);
		gpio_map.put((byte) 4, RaspiPin.GPIO_04);
		gpio_map.put((byte) 5, RaspiPin.GPIO_05);
		gpio_map.put((byte) 6, RaspiPin.GPIO_06);
		gpio_map.put((byte) 7, RaspiPin.GPIO_07);
		gpio_map.put((byte) 8, RaspiPin.GPIO_08);
		gpio_map.put((byte) 9, RaspiPin.GPIO_09);
		gpio_map.put((byte) 10, RaspiPin.GPIO_10);
		gpio_map.put((byte) 11, RaspiPin.GPIO_11);
		gpio_map.put((byte) 12, RaspiPin.GPIO_12);
		gpio_map.put((byte) 13, RaspiPin.GPIO_13);
		gpio_map.put((byte) 14, RaspiPin.GPIO_14);
		gpio_map.put((byte) 15, RaspiPin.GPIO_15);
		gpio_map.put((byte) 16, RaspiPin.GPIO_16);
		gpio_map.put((byte) 17, RaspiPin.GPIO_17);
		gpio_map.put((byte) 18, RaspiPin.GPIO_18);
		gpio_map.put((byte) 19, RaspiPin.GPIO_19);
		gpio_map.put((byte) 20, RaspiPin.GPIO_20);
		gpio_map.put((byte) 21, RaspiPin.GPIO_21);
		gpio_map.put((byte) 22, RaspiPin.GPIO_22);
		gpio_map.put((byte) 23, RaspiPin.GPIO_23);
		gpio_map.put((byte) 24, RaspiPin.GPIO_24);
		gpio_map.put((byte) 25, RaspiPin.GPIO_25);
		gpio_map.put((byte) 26, RaspiPin.GPIO_26);
		gpio_map.put((byte) 27, RaspiPin.GPIO_27);
		gpio_map.put((byte) 28, RaspiPin.GPIO_28);
		gpio_map.put((byte) 29, RaspiPin.GPIO_29);
		gpio_map.put((byte) 30, RaspiPin.GPIO_30);
		gpio_map.put((byte) 31, RaspiPin.GPIO_31);
	}
	
	/**
	 * Returns the corresponding Pin for a Byte value
	 * @param gpioPin
	 * @return a Pin representing the gpioPin number
	 */
	public static Pin getPin(Byte gpioPin) {
		return gpio_map.get(gpioPin);
	}
}
