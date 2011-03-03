package net.cellar.config;


import net.cellar.core.event.Event;

/**
 * @author iocanel
 */
public class RemoteConfigurationEvent extends Event {

	public RemoteConfigurationEvent(String id) {
		super(id);
	}
}
