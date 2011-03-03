package net.cellar.features;

import net.cellar.core.ClusterManager;
import net.cellar.core.event.EventBlocking;
import net.cellar.core.event.EventProducer;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.features.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * @author iocanel
 */
public class FeaturesSynchronizer extends FeaturesSupport {

	private static Logger logger = LoggerFactory.getLogger(FeaturesSynchronizer.class);

	private EventProducer producer;

	/**
	 * Initialization method
	 */
	public void init() {
		super.init();
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
		super.destroy();
	}

	/**
	 * Pulls the features from the cluster.
	 */
	public void pull() {
		//Retrieve remote feautre URLs.
		if (repositories != null && !repositories.isEmpty()) {
			for (String url : repositories) {
				try {
					logger.debug("Adding repository {}",url);
					featuresService.addRepository(new URI(url));
				} catch (MalformedURLException e) {
					logger.error("Failed to add features repository! Url {} is malformed", url);
				} catch (Exception e) {
					logger.error("Failed to add features repository.", e);
				}
			}
		}

		//Retrieve remote feautre status.
		if (features != null && !features.isEmpty()) {
			for (FeatureInfo info : features.keySet()) {
				String name = info.getName();
				//Check if feature is blocked.
				if (!isBlocked(name, EventBlocking.INBOUND)) {
					Boolean remotelyInstalled = features.get(info);
					Boolean localyInstalled = isInstanlled(info.getName(), info.getVersion());

					//If feature needs to be installed locally.
					if (remotelyInstalled && !localyInstalled) {
						try {
							logger.debug("Installing feature {} version {}", info.getName(), info.getVersion());
							featuresService.installFeature(info.getName(), info.getVersion());
						} catch (Exception e) {
							logger.error("Failed to install feature {} {} ", info.getName(), info.getVersion());
						}
						//If feature needs to be localy uninstalled.
					} else if (!remotelyInstalled && localyInstalled) {
						try {
							logger.debug("Uninstalling feature {} version {}", info.getName(), info.getVersion());
							featuresService.uninstallFeature(info.getName(), info.getVersion());
						} catch (Exception e) {
							logger.error("Failed to uninstall feature {} {} ", info.getName(), info.getVersion());
						}
					}
				} else logger.debug("Feature with name {} is marked as BLOCKED INBOUND");
			}
		}
	}

	/**
	 * Pushes features to the cluster.
	 */
	public void push() {
		Repository[] repositoryList = new Repository[0];
		Feature[] featuresList = new Feature[0];

		try {
			repositoryList = featuresService.listRepositories();
			featuresList = featuresService.listFeatures();
		} catch (Exception e) {
			logger.error("Error listing features.", e);
		}

		//Process repository list
		 if (repositoryList != null && repositoryList.length > 0) {
			for (Repository repository : repositoryList) {
				pushRepository(repository);
			}
		}

		//Process features list
		if (featuresList != null && featuresList.length > 0) {
			for (Feature feature : featuresList) {
				pushFeature(feature);
			}
		}
	}



	public EventProducer getProducer() {
		return producer;
	}

	public void setProducer(EventProducer producer) {
		this.producer = producer;
	}


	public ClusterManager getCollectionManager() {
		return clusterManager;
	}

	public void setCollectionManager(ClusterManager clusterManager) {
		this.clusterManager = clusterManager;
	}

	public FeaturesService getFeaturesService() {
		return featuresService;
	}

	public void setFeaturesService(FeaturesService featuresService) {
		this.featuresService = featuresService;
	}
}
