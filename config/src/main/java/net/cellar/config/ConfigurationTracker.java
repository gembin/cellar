package net.cellar.config;

import net.cellar.core.event.EventTracker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author iocanel
 */
public class ConfigurationTracker implements EventTracker<RemoteConfigurationEvent> {

	private ConcurrentMap<String, RemoteConfigurationEvent> pending = new ConcurrentHashMap<String, RemoteConfigurationEvent>();

	public void start(RemoteConfigurationEvent event) {
		pending.put(event.getId(), event);
	}

	public void stop(RemoteConfigurationEvent event) {
		pending.remove(event.getId());
	}

	public Boolean isPending(RemoteConfigurationEvent event) {
		return pending.containsKey(event.getId());
	}
}
