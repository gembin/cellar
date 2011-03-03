package net.cellar.core.event;

import net.cellar.core.Handler;
import net.cellar.core.HandlerRegistry;

/**
 * @author iocanel
 */
public interface EventHandlerRegistry<E extends Event>  extends HandlerRegistry<E,Handler<E>> {

	/**
	 * Returns the {@code EventHandler} for the given {@code Event}.
	 * @param event
	 * @return
	 */
	public EventHandler<E> getHandler(E event);
}
