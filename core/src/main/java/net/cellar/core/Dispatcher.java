package net.cellar.core;

import java.io.Serializable;

/**
 * @author iocanel
 */
public interface Dispatcher<T extends Serializable> {

	/**
	 * Dispatches the object to the appropriate handler.
	 * @param obj
	 */
	public void dispatch(T obj);
}
