package net.cellar.features;

import net.cellar.core.event.Event;
import org.apache.karaf.features.FeatureEvent.EventType;

/**
 * @author iocanel
 */
public class RemoteFeaturesEvent extends Event {

	private static final String separator = "/";

	private String name;
	private String version;
	private EventType type;

	public RemoteFeaturesEvent(String name, String version, EventType type) {
		super(name+separator+version);
		this.name=name;
		this.version=version;
		this.type=type;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public EventType getType() {
		return type;
	}
}
