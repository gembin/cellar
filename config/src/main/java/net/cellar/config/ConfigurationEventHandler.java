package net.cellar.config;


import net.cellar.core.Configurations;
import net.cellar.core.Group;
import net.cellar.core.Node;
import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;
import net.cellar.core.control.SwitchStatus;
import net.cellar.core.event.EventHandler;
import net.cellar.core.event.EventType;
import org.osgi.service.cm.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Map;
import java.util.Properties;

/**
 * @author iocanel
 */
public class ConfigurationEventHandler extends ConfigurationSupport implements EventHandler<RemoteConfigurationEvent> {

    private static Logger logger = LoggerFactory.getLogger(ConfigurationEventHandler.class);
    public static final String SWITCH_ID = "net.cellar.configuration.handler";

    private final Switch eventSwitch = new BasicSwitch(SWITCH_ID);

    private Node node;

    /**
     * Handles a
     *
     * @param event
     */
    public void handle(RemoteConfigurationEvent event) {

        if (event == null || event.getSourceGroup() == null || node == null || node.equals(event.getSourceNode()))
            return;

        Group group = event.getSourceGroup();
        String groupName = group.getName();

        Map<String, Properties> configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP + Configurations.SEPARATOR + groupName);

        if (eventSwitch.getStatus().equals(SwitchStatus.ON)) {
            String pid = event.getId();
            //Check if the pid is marked as local.
            if (isAllowed(event.getSourceGroup(), Constants.CATEGORY, pid, EventType.INBOUND)) {
                Properties dictionary = configurationTable.get(pid);
                Configuration conf;
                try {
                    conf = configurationAdmin.getConfiguration(pid);
                    //Update the configurationTable.
                    if (conf != null && dictionary != null) {
                        Dictionary existingConfiguration = filterDictionary(conf.getProperties());
                        if (!dictionariesEqual(dictionary, existingConfiguration)) {
                            conf.update(preparePull(dictionary));
                        }
                    }
                } catch (IOException ex) {
                    logger.error("Failed to read remote configurationTable", ex);
                }
            } else logger.debug("Configuration with pid {} is marked as local.", pid);
        }
    }


    /**
     * Initialization Method.
     */
    public void init() {
        if (clusterManager != null) {
            node = clusterManager.getNode();
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
}
