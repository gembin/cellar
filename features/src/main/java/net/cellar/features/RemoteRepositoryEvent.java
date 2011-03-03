package net.cellar.features;

import net.cellar.core.event.Event;
import org.apache.karaf.features.RepositoryEvent.EventType;

/**
 * @author iocanel
 */
public class RemoteRepositoryEvent extends Event {

	private EventType type;

	public RemoteRepositoryEvent(String id,EventType type) {
		super(id);
		this.type=type;
	}

	public EventType getType() {
		return type;
	}
}
