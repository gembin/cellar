package net.cellar.core.event;

import net.cellar.core.Producer;

/**
 * Transmits events to the cluster.
 *
 * @author iocanel
 */
public interface EventProducer<E extends Event> extends Producer<E> {

	/**
	 * Produce {@code Event}s to the cluster.
	 *
	 * @param event
	 */
	public void produce(E event);
}
