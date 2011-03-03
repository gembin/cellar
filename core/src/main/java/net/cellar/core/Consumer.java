package net.cellar.core;

import net.cellar.core.control.Switch;

import java.io.Serializable;

/**
 * Generic Consumer Interface
 * @author iocanel
 */
public interface Consumer<T extends Serializable> {

	/**
	 * Consume an object.
	 * @param obj
	 */
	public void consume(T obj);

    /**
	 * Returns the {@code Switch}.
	 * @return
	 */
	public Switch getSwitch();

}
