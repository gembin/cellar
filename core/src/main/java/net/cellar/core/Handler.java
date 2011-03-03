package net.cellar.core;

import net.cellar.core.control.Switch;

import java.io.Serializable;

/**
 * @author iocanel
 */
public interface Handler<T extends Serializable> {


	/**
	 * Returns the Class of the object that is to be handled.
	 * @return
	 */
	public Class<T> getType();

	/**
	 * Returns the {@code Switch}.
	 * @return
	 */
	public Switch getSwitch();
}
