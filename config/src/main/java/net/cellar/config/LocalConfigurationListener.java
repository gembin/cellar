package net.cellar.config;

import net.cellar.core.ClusterManager;
import net.cellar.core.event.EventBlocking;
import net.cellar.core.event.EventProducer;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author: iocanel
 */
public class LocalConfigurationListener extends ConfigurationSupport implements ConfigurationListener {

	private static final Logger logger = LoggerFactory.getLogger(LocalConfigurationListener.class);

	private EventProducer producer;
	private ConfigurationTracker tracker;
	private ClusterManager clusterManager;

	private Map<String, Properties> configurationTable;

	/**
	 * Handle local configuration events.
	 * If the event is a pending event stop it. Else broadcast it to the cluster.
	 *
	 * @param event
	 */
	public void configurationEvent(ConfigurationEvent event) {
		String pid = event.getPid();
		//Check if the pid is marked as local.
		if (!isBlocked(pid, EventBlocking.OUTBOUND)) {
			RemoteConfigurationEvent configurationEvent = new RemoteConfigurationEvent(pid);
			push(pid);
			if (!tracker.isPending(configurationEvent)) {
				producer.produce(configurationEvent);
			} else tracker.stop(configurationEvent);
		} else logger.debug("Configuration with pid {} is marked as local.",pid);
	}

	/**
	 * Push configuration with pid to the table.
	 *
	 * @param pid
	 */
	protected void push(String pid) {
		Map<String, Properties> configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP);
		try {
			Configuration[] configurations = configurationAdmin.listConfigurations("(service.pid=" + pid + ")");
			for (Configuration configuration : configurations) {
				Properties properties = dictionaryToProperties(configuration.getProperties());
				configurationTable.put(configuration.getPid(), properties);
			}
		} catch (IOException e) {
			logger.error("Failed to push configuration with pid:" + pid, e);
		} catch (InvalidSyntaxException e) {
			logger.error("Failed to retrieve configuration with pid:" + pid, e);
		}
	}

	public EventProducer getProducer() {
		return producer;
	}

	public void setProducer(EventProducer producer) {
		this.producer = producer;
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
