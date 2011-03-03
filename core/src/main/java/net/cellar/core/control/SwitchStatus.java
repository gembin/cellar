package net.cellar.core.control;

/**
 * @author iocanel
 */
public enum SwitchStatus {

	ON(true),
	OFF(false);

	private Boolean value;

	SwitchStatus(Boolean value) {
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}
}
