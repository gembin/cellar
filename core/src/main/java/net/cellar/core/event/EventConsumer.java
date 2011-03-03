package net.cellar.core.event;

import net.cellar.core.Consumer;

/**
 * @author iocanel
 */
public interface EventConsumer<E extends Event> extends Consumer<E> {

	/**
	 * Consumes {@code Event}s to the cluster.
	 *
	 * @param event
	 */
	public void consume(E event);
}
