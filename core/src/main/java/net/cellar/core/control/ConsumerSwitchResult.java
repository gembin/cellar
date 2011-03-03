package net.cellar.core.control;

import net.cellar.core.command.Result;

/**
 * @author iocanel
 */
public class ConsumerSwitchResult extends Result {

	protected Boolean sucess = Boolean.TRUE;

	/**
	 * Constructor
	 * @param id
	 */
	public ConsumerSwitchResult(String id) {
		super(id);
	}

	/**
	 * Constructor
	 * @param id
	 * @param sucess
	 */
	public ConsumerSwitchResult(String id, Boolean sucess) {
		super(id);
		this.sucess = sucess;
	}

	public Boolean getSucess() {
		return sucess;
	}

	public void setSucess(Boolean sucess) {
		this.sucess = sucess;
	}
}
