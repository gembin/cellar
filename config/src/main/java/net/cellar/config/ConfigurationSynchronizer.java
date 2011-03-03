package net.cellar.config;

import net.cellar.core.ClusterManager;
import net.cellar.core.event.EventBlocking;
import net.cellar.core.event.EventProducer;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @author iocanel
 */
public class ConfigurationSynchronizer extends ConfigurationSupport {

	private static Logger logger = LoggerFactory.getLogger(ConfigurationSynchronizer.class);

	private EventProducer producer;
	private ClusterManager clusterManager;
	private Map<String, Properties> configurationTable;

	/**
	 * Constructor
	 */
	public ConfigurationSynchronizer() {
	}

	/**
	 * Registration method
	 */
	public void init() {
		if (clusterManager != null) {
			configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP);
		}
		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			pull();
			push();
		} finally {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		}
	}

	/**
	 * Destruction method
	 */
	public void destroy() {

	}

	/**
	 * Reads the configuration from the remote map.
	 */
	public void pull() {
		for (String pid : configurationTable.keySet()) {
			//Check if the pid is marked as local.
			if (!isBlocked(pid, EventBlocking.INBOUND)) {
				Properties dictionary = configurationTable.get(pid);
				try {
					Configuration conf = configurationAdmin.getConfiguration(pid);
					//Update the configuration.
					if (conf != null) {
						//Mark the remote configuration event.
						RemoteConfigurationEvent event = new RemoteConfigurationEvent(conf.getPid());
						conf.update(dictionary);
					}
				} catch (IOException ex) {
					logger.error("Failed to read remote configuration", ex);
				}
			}
		}
	}

	/**
	 * Publishses local configuration to the cluster.
	 */
	public void push() {
		Configuration[] configs;
		try {
			configs = configurationAdmin.listConfigurations(null);
			for (Configuration conf : configs) {
				String pid = conf.getPid();
				//Check if the pid is marked as local.
				if (!isBlocked(pid, EventBlocking.OUTBOUND)) {
					Properties source = dictionaryToProperties(conf.getProperties());
					Properties target = configurationTable.get(pid);
					if (target != null) {
						boolean requiresUpdate = false;
						if (source != null && source.keys() != null) {
							Enumeration keys = source.keys();
							while (keys.hasMoreElements()) {
								String key = (String) keys.nextElement();
								if (target.get(key) == null || target.get(key).equals(source.get(key))) {
									requiresUpdate = true;
									target.put(key, source.get(key));
								}
							}
							configurationTable.put(pid, target);
							if (requiresUpdate) {
								RemoteConfigurationEvent event = new RemoteConfigurationEvent(conf.getPid());
								producer.produce(event);
							}
							logger.info("Publishing PID:" + pid);
						}
					} else {
						RemoteConfigurationEvent event = new RemoteConfigurationEvent(conf.getPid());
						configurationTable.put(pid, source);
						producer.produce(event);
						logger.info("Publishing PID:" + pid);
					}
				}
			}
		} catch (IOException ex) {
			logger.error("Failed to read remote configuration, due to I/O error:", ex);
		} catch (InvalidSyntaxException ex) {
			logger.error("Failed to read remote configuration, due to invalid filter syntax:", ex);
		}
	}


	public EventProducer getProducer() {
		return producer;
	}

	public void setProducer(EventProducer producer) {
		this.producer = producer;
	}

	public ClusterManager getClusterManager() {
		return clusterManager;
	}

	public void setClusterManager(ClusterManager clusterManager) {
		this.clusterManager = clusterManager;
	}
}
