package net.cellar.core.control;

/**
 * @author iocanel
 */
public class BasicSwitch implements Switch {

	private SwitchStatus status = SwitchStatus.ON;
	private String name;

	/**
	 * Constructor
	 * @param name
	 */
	public BasicSwitch(String name) {
		this.name = name;
	}

	/**
	 * Constructor
	 * @param name
	 * @param status
	 */
	public BasicSwitch(String name,SwitchStatus status) {
		this.status = status;
		this.name = name;
	}

	public void turnOn() {
		this.status = SwitchStatus.ON;
	}

	public void turnOff() {
		this.status = SwitchStatus.OFF;
	}

	/**
	 * Returns the status of the {@code Switch}.
	 * @return
	 */
	public SwitchStatus getStatus() {
		return status;
	}

	/**
	 * Returns the name of the  {@code Switch}.
	 * @return
	 */
	public String getName() {
		return name;
	}
}
