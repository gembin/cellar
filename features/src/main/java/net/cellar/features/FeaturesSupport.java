package net.cellar.features;

import net.cellar.core.ClusterManager;
import net.cellar.core.event.EventBlocking;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.features.Repository;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * @author iocanel
 */
public class FeaturesSupport {

	private static Logger logger = LoggerFactory.getLogger(FeaturesSupport.class);

	protected FeaturesService featuresService;
	protected ClusterManager clusterManager;

    protected List<String> repositories;
	protected Map<FeatureInfo, Boolean> features;
	protected ConfigurationAdmin configurationAdmin;

	/**
	 * Initialization method
	 */
	public void init() {
		if (clusterManager != null) {
			repositories = clusterManager.getList(Constants.REPOSITORIES);
			features = clusterManager.getMap(Constants.FEATURES);
		}
	}

	/**
	 * Destruction method
	 */
	public void destroy() {

	}

	/**
	 * Returns true if the specified feature is installed.
	 * @param name
	 * @param version
	 * @return
	 */
	public Boolean isInstanlled(String name, String version) {
		if (featuresService != null) {
			Feature[] features = featuresService.listInstalledFeatures();

			if (features != null && features.length > 0) {
				for (Feature feature : features) {
					if (feature.getName().equals(name) && feature.getVersion().equals(version))
						return true;
				}
			}
		}
		return false;
	}

		/**
	 * Returns true if PID is marked as local.
	 * @param feautreName
	 * @param blocking
	 * @return
	 */
	public Boolean isBlocked(String feautreName, EventBlocking blocking) {
	  Boolean result = Boolean.FALSE;
		try {
			Configuration conf = configurationAdmin.getConfiguration(Constants.FEATURES_CFG);
			if (conf != null) {
				Dictionary<String,String> properties = conf.getProperties();
				if (properties != null) {
					String confguredBlockingType = properties.get(feautreName);
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

	/**
	 * Pushes a {@code Feature} and its status to the distributed list of features.
	 * @param feature
	 */
	public void pushFeature(Feature feature) {
		if (feature != null) {
			if (!isBlocked(feature.getName(), EventBlocking.OUTBOUND)) {
				if (featuresService != null && features != null) {
					FeatureInfo info = new FeatureInfo(feature.getName(), feature.getVersion());
					Boolean installed = featuresService.isInstalled(feature);
					features.put(info, installed);
				}
			} else logger.debug("Feature with name {} is marked as BLOCKED OUTBOUND");
		} else logger.debug("Feature is null");
	}

	/**
	 * Pushed a {@code Repository} to the distributed list of repositories.
	 * @param repository
	 */
    public void pushRepository(Repository repository) {
	     if (featuresService != null && repositories != null) {
		     URI uri = repository.getURI();
		     repositories.add(uri.toString());
	     }
    }

	public ClusterManager getClusterManager() {
		return clusterManager;
	}

	public void setClusterManager(ClusterManager clusterManager) {
		this.clusterManager = clusterManager;
	}

	public FeaturesService getFeaturesService() {
		return featuresService;
	}

	public void setFeaturesService(FeaturesService featuresService) {
		this.featuresService = featuresService;
	}

	public ConfigurationAdmin getConfigurationAdmin() {
		return configurationAdmin;
	}

	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}
}
