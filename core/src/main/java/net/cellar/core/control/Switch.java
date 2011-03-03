package net.cellar.core.control;

/**
 * An interface that describes objects that can be turned on/off and act like a switch.
 * @author iocanel
 */
public interface Switch {

	/**
	 * Returns the name of the Switch.
	 * @return
	 */
	public String getName();

    /**
	 * Turns on.
	 */
	public void turnOn();

	/**
	 * Turns off
	 */
	public void turnOff();

	/**
	 * Returns the status of the switch.
	 * @return
	 */
	public SwitchStatus getStatus();
}
