package net.cellar.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author iocanel
 */
public class EventHandlerServiceRegistryDispatcher<E extends Event> implements EventDispatcher<E> {

	private static final Logger logger = LoggerFactory.getLogger(EventHandlerServiceRegistryDispatcher.class);

	private ExecutorService threadPool;
	private EventHandlerServiceRegistry handlerRegistry;

	/**
	 * Initialization
	 */
	public void init() {
		if(threadPool == null) {
			threadPool = Executors.newCachedThreadPool();
		}
	}

	/**
	 * Dispatches an {@code Event} to the appropriate {@code EventHandler}.
	 * @param event
	 */
	public void dispatch(E event) {
		EventDispatchTask task = new EventDispatchTask(event,handlerRegistry);
		threadPool.execute(task);
	}

	public EventHandlerServiceRegistry getHandlerRegistry() {
		return handlerRegistry;
	}

	public void setHandlerRegistry(EventHandlerServiceRegistry handlerRegistry) {
		this.handlerRegistry = handlerRegistry;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}
}
