package net.cellar.config;


import net.cellar.core.ClusterManager;
import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;
import net.cellar.core.control.SwitchStatus;
import net.cellar.core.event.EventBlocking;
import net.cellar.core.event.EventHandler;
import org.osgi.service.cm.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author iocanel
 */
public class ConfigurationEventHandler extends ConfigurationSupport implements EventHandler<RemoteConfigurationEvent> {

	private static Logger logger = LoggerFactory.getLogger(ConfigurationEventHandler.class);
	public static final String SWITCH_ID = "net.cellar.configuration.handler";

	private final Switch eventSwitch = new BasicSwitch(SWITCH_ID);
	private ConfigurationTracker tracker;
	private ClusterManager clusterManager;

	private Map<String, Properties> configurationTable;

	/**
	 * Handles a
	 * @param event
	 */
	public void handle(RemoteConfigurationEvent event) {
		if (eventSwitch.getStatus().equals(SwitchStatus.ON)) {
			String pid = event.getId();
			//Check if the pid is marked as local.
			if (!isBlocked(pid, EventBlocking.INBOUND)) {
				tracker.start(event);
				Properties dictionary = configurationTable.get(pid);
				Configuration conf;
				try {
					conf = configurationAdmin.getConfiguration(pid);
					//Update the configurationTable.
					if (conf != null && dictionary != null) {
						//Mark the remote configurationTable event.
						conf.update(dictionary);
					}
				} catch (IOException ex) {
					logger.error("Failed to read remote configurationTable", ex);
				}
			} else logger.debug("Configuration with pid {} is marked as local.",pid);
		}
	}

	/**
	 * Initialization Method.
	 */
	public void init() {
		if (clusterManager != null) {
			configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP);
		}
	}

	/**
	 * Destruction Method.
	 */
	public void destroy() {

	}


	public Switch getSwitch() {
		return eventSwitch;
	}

	public Class<RemoteConfigurationEvent> getType() {
		return RemoteConfigurationEvent.class;
	}

	public ConfigurationTracker getTracker() {
		return tracker;
	}

	public void setTracker(ConfigurationTracker tracker) {
		this.tracker = tracker;
	}

	public ClusterManager getClusterManager() {
		return clusterManager;
	}

	public void setClusterManager(ClusterManager clusterManager) {
		this.clusterManager = clusterManager;
	}
}
