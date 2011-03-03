package net.cellar.core.event;


import net.cellar.core.Handler;

public interface EventHandler<E extends Event> extends Handler<E> {

    public static String MANAGED_FILTER="(managed=true)";

	/**
	 * Called to handle {@code Event}.
	 */
	public void handle(E event);
}
