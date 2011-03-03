package net.cellar.features;

import net.cellar.core.event.EventBlocking;
import net.cellar.core.event.EventProducer;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeatureEvent;
import org.apache.karaf.features.RepositoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iocanel
 */
public class LocalFeaturesListener extends FeaturesSupport implements org.apache.karaf.features.FeaturesListener {

	private static Logger logger = LoggerFactory.getLogger(LocalFeaturesListener.class);
	private EventProducer producer;

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	/**
	 * Called when a {@code FeatureEvent} occurs.
	 *
	 * @param event
	 */
	public void featureEvent(FeatureEvent event) {
		if (event != null) {
			Feature feature = event.getFeature();
			String name = feature.getName();
			String version = feature.getVersion();
			if (!isBlocked(name, EventBlocking.OUTBOUND)) {
				FeatureEvent.EventType type = event.getType();
				pushFeature(event.getFeature());
				RemoteFeaturesEvent featureEvent = new RemoteFeaturesEvent(name, version, type);
				producer.produce(featureEvent);
			} else logger.debug("Feature with name {} is marked as local.", name);
		}
	}

	/**
	 * Called when a {@code RepositoryEvent} occurs.
	 *
	 * @param event
	 */
	public void repositoryEvent(RepositoryEvent event) {
		if (event != null && event.getRepository() != null) {
			RemoteRepositoryEvent repositoryEvent = new RemoteRepositoryEvent(event.getRepository().getURI().toString(), event.getType());
			pushRepository(event.getRepository());
			producer.produce(repositoryEvent);
		}
	}
	public EventProducer getProducer() {
		return producer;
	}

	public void setProducer(EventProducer producer) {
		this.producer = producer;
	}
}
