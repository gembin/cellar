package net.cellar.core;

import java.io.Serializable;

/**
 * @author iocanel
 */
public interface HandlerRegistry<T extends Serializable, H extends Handler<T>> {

	/**
	 * Returns the {@code Handler} for the given object.
	 * @param obj
	 * @return
	 */
	public H getHandler(T obj);
}
