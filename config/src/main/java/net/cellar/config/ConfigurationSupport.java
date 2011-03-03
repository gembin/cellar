package net.cellar.config;

import net.cellar.core.event.EventBlocking;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author iocanel
 */
public class ConfigurationSupport {

	private static Logger logger = LoggerFactory.getLogger(ConfigurationSupport.class);
	protected ConfigurationAdmin configurationAdmin;

	/**
	 * Reads a {@code Dictionary} object and creates a property object out of it.
	 *
	 * @param dictionary
	 * @return
	 */
	public Properties dictionaryToProperties(Dictionary dictionary) {
		Properties properties = new Properties();
		if (dictionary != null && dictionary.keys() != null) {

			Enumeration keys = dictionary.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				if (key != null && dictionary.get(key) != null) {
					properties.put(key, dictionary.get(key));
				}
			}
		}
		return properties;
	}

	/**
	 * Returns true if PID is marked as local.
	 * @param pid
	 * @param blocking
	 * @return
	 */
	public Boolean isBlocked(String pid, EventBlocking blocking) {
	  Boolean result = Boolean.FALSE;
		try {
			Configuration conf = configurationAdmin.getConfiguration(Constants.CONFIGURATION_CFG);
			if (conf != null) {
				Dictionary<String, String> properties = conf.getProperties();
				if (properties != null) {
					String confguredBlockingType = properties.get(pid);
					if (confguredBlockingType != null && (confguredBlockingType.equals(EventBlocking.FULL.name()) || confguredBlockingType.equals(blocking.name()))) {
						result = Boolean.TRUE;
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error looking up for clustering configuration cfg");
		}
		return result;
	}

	public ConfigurationAdmin getConfigurationAdmin() {
		return configurationAdmin;
	}

	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}
}
