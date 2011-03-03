package net.cellar.core;

import net.cellar.core.control.Switch;

import java.io.Serializable;

/**
 * Generic Producer Interface.
 * @author iocanel
 */
public interface Producer<T extends Serializable> {

	/**
	 * Produce an object.
	 * @param obj
	 */
	public void produce(T obj);

	/**
	 * Returns the {@code Switch}.
	 * @return
	 */
	public Switch getSwitch();
}
