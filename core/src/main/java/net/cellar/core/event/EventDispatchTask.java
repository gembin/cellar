package net.cellar.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iocanel
 */
public class EventDispatchTask<E extends Event> implements Runnable {

	private Logger logger = LoggerFactory.getLogger(EventDispatchTask.class);
	private E event;
	private EventHandlerRegistry handlerRegistry;
	private long timeout=10000;
	private long interval=1000;

	/**
	 * Constructor
	 * @param event
	 * @param handlerRegistry
	 */
	public EventDispatchTask(E event, EventHandlerRegistry handlerRegistry) {
		this.event = event;
		this.handlerRegistry = handlerRegistry;
	}

	/***
	 * Constructor
	 * @param event
	 * @param handlerRegistry
	 * @param timeout
	 */
	public EventDispatchTask(E event, EventHandlerRegistry handlerRegistry, long timeout) {
		this.event = event;
		this.handlerRegistry = handlerRegistry;
		this.timeout = timeout;
	}

	/**
	 * Constructor
	 * @param handlerRegistry
	 * @param timeout
	 * @param interval
	 * @param event
	 */
	public EventDispatchTask(EventHandlerRegistry handlerRegistry, long timeout, long interval, E event) {
		this.handlerRegistry = handlerRegistry;
		this.timeout = timeout;
		this.interval = interval;
		this.event = event;
	}

	public void run() {
		boolean dispathced=false;

		for (long delay = 0; delay < timeout && !dispathced; delay += interval) {
			EventHandler handler = handlerRegistry.getHandler(event);
			if (handler != null) {
				handler.handle(event);
				dispathced=true;
			} else {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
				   logger.warn("Interupted while waiting for event handler",e);
				}
			}
		}
		if(!dispathced) {
			logger.warn("Failed to retrieve handler for event {}",event.getClass());
		}
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}
