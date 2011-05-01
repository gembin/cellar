package net.cellar.features.shell;

import net.cellar.core.Configurations;
import net.cellar.core.Group;
import net.cellar.core.shell.CellarCommandSupport;
import net.cellar.features.Constants;
import net.cellar.features.FeatureInfo;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author: iocanel
 */
@Command(scope = "cluster", name = "feature-install", description = "Installs a feature to all members of a group")
public abstract class FeatureCommandSupport extends CellarCommandSupport {

    protected static final Logger logger = LoggerFactory.getLogger(FeatureCommandSupport.class);

    protected FeaturesService featuresService;
    protected BundleContext bundleContext;


    /**
     * Forces the features status for a specific group.
     * Why? Its required if no group member currently in the cluster.
     * If a member of the group joins later, it won't find the change, unless we force it.
     *
     * @param groupName
     * @param feature
     * @param version
     * @param status
     */
    public Boolean updateFeatureStatus(String groupName, String feature, String version, Boolean status) {
        Boolean result = Boolean.FALSE;
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            Group group = groupManager.findGroupByName(groupName);
            if (group == null || group.getMembers().isEmpty()) {
                FeatureInfo info = new FeatureInfo(feature, version);
                Map<FeatureInfo, Boolean> features = clusterManager.getMap(Constants.FEATURES + Configurations.SEPARATOR + groupName);
                //1st check the existing configuration
                if (version == null || version.isEmpty()) {
                    for (FeatureInfo f : features.keySet()) {
                        if (f.getName().equals(feature)) {
                            version = f.getVersion();
                            info.setVersion(version);
                        }
                    }
                }

                //2nd check the Features Service.
                try {
                    for (Feature f : featuresService.listFeatures()) {
                        if (f.getName().equals(feature)) {
                            version = f.getVersion();
                            info.setVersion(version);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error while browsing features", e);
                }

                if (info.getVersion() != null && !info.getVersion().isEmpty()) {
                    features.put(info, status);
                    result = Boolean.TRUE;
                }
            }
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
        return result;
    }


    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public FeaturesService getFeaturesService() {
        return featuresService;
    }

    public void setFeaturesService(FeaturesService featuresService) {
        this.featuresService = featuresService;
    }
}
