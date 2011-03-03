package net.cellar.core.control;

import net.cellar.core.command.Command;

/**
 * @author iocanel
 */
public class ConsumerSwitchCommand extends Command<ConsumerSwitchResult> {

	private SwitchStatus status = null;

	/**
	 * Constructor
	 * @param id
	 */
	public ConsumerSwitchCommand(String id) {
		super(id);
	}

	/**
	 * Constructor
	 * @param id
	 * @param status
	 */
	public ConsumerSwitchCommand(String id, SwitchStatus status) {
		super(id);
		this.status = status;
	}

	@Override
	public Boolean getBypassSwitches() {
		return true;
	}

	/**
	 * Returns the {@code SwitchStatus}
	 * @return
	 */
	public SwitchStatus getStatus() {
		return status;
	}

	/**
	 * Sets the {@code SwitchStatus}
	 * @param status
	 */
	public void setStatus(SwitchStatus status) {
		this.status = status;
	}
}
