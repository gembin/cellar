package net.cellar.features;


import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;
import net.cellar.core.event.EventBlocking;
import net.cellar.core.event.EventHandler;
import org.apache.karaf.features.FeatureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iocanel
 */
public class FeaturesEventHandler extends FeaturesSupport implements EventHandler<RemoteFeaturesEvent> {

	private static Logger logger = LoggerFactory.getLogger(FeaturesSynchronizer.class);
	public static final String SWITCH_ID = "net.cellar.event.features.handler";

	private final Switch eventSwitch = new BasicSwitch(SWITCH_ID);

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	/**
	 * Features Event.
	 *
	 * @param event
	 */
	public void handle(RemoteFeaturesEvent event) {
		String name = event.getName();
		String version = event.getVersion();
		if (!isBlocked(name, EventBlocking.INBOUND)) {
			logger.debug("Received features event {} version {} type {}.", new Object[]{event.getName(), event.getVersion(), event.getType()});
			FeatureEvent.EventType type = event.getType();
			Boolean isInstalled = isInstanlled(name, version);
			try {
				if (FeatureEvent.EventType.FeatureInstalled.equals(type) && !isInstalled) {
					logger.debug("Installing feature {} version {}", name, version);
					featuresService.installFeature(name, version);
				} else if (FeatureEvent.EventType.FeatureUninstalled.equals(type) && isInstalled) {
					logger.debug("Uninstalling feature {} version {}", name, version);
					featuresService.uninstallFeature(name, version);
				}
			} catch (Exception e) {
				logger.error("Failed to process feature event.", e);
			}
		} else logger.debug("Feature with name {} is marked as BLOCKED INBOUND", name);
	}

	public Class<RemoteFeaturesEvent> getType() {
		return RemoteFeaturesEvent.class;
	}

	public Switch getSwitch() {
		return eventSwitch;
	}
}
