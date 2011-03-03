package net.cellar.core.event;

import net.cellar.core.Dispatcher;

/**
 * A dispatcher which dispatches each event to the appropriate Handler.
 *
 * @author iocanel
 */
public interface EventDispatcher<E extends Event> extends Dispatcher<E> {


	/**
	 * Dispatches the Event to the appropriate handler.
	 *
	 * @param event
	 */
	public void dispatch(E event);
}
