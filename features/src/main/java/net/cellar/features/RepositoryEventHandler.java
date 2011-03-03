package net.cellar.features;

import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;
import net.cellar.core.event.EventHandler;
import org.apache.karaf.features.RepositoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author iocanel
 */
public class RepositoryEventHandler extends FeaturesSupport implements EventHandler<RemoteRepositoryEvent> {

	private static Logger logger = LoggerFactory.getLogger(RepositoryEventHandler.class);
	public static final String SWITCH_ID = "net.cellar.event.repository.handler";

	private final Switch eventSwitch = new BasicSwitch(SWITCH_ID);

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	public void handle(RemoteRepositoryEvent event) {
		String uri = event.getId();
		RepositoryEvent.EventType type = event.getType();
		try {
			if (RepositoryEvent.EventType.RepositoryAdded.equals(type)) {
				logger.debug("Adding repository url {}", uri);
				featuresService.addRepository(new URI(uri));
			} else {
				logger.debug("Removing repository url {}", uri);
				featuresService.removeRepository(new URI(uri));
			}
		} catch (Exception e) {
			logger.error("Failed to add/remove repository {}", uri);
		}
	}

	public Class<RemoteRepositoryEvent> getType() {
		return RemoteRepositoryEvent.class;
	}

	public Switch getSwitch() {
		return eventSwitch;
	}
}
