package net.cellar.core.event;

/**
 * @author iocanel
 */
public interface EventTracker<E extends Event> {

	/**
	 * Starts tracking the occurance of an {@code Event}.
	 * @param event
	 */
	public void start(E event);

	/**
	 * Stops tracking the occurance of an {@code Event}.
	 * @param event
	 */
	public void stop(E event);

	/**
	 * Returns true if the {@code Event} is being tracked.
	 * @param event
	 * @return
	 */
	public Boolean isPending(E event);
}
