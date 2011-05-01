package net.cellar.config.shell.completers;

import net.cellar.config.Constants;
import net.cellar.core.ClusterManager;
import net.cellar.core.Configurations;
import net.cellar.core.Group;
import net.cellar.core.GroupManager;
import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: iocanel
 */
public class ClusterConfigCompleter implements Completer {

    protected ClusterManager clusterManager;
    protected GroupManager groupManager;

    public int complete(String buffer, int cursor, List<String> candidates) {
        StringsCompleter delegate = new StringsCompleter();
        try {
            Map<String, Group> groups = groupManager.listGroups();
            if (groups != null && !groups.isEmpty()) {
                for (String groupName : groups.keySet()) {
                    Map<String, Properties> configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP + Configurations.SEPARATOR + groupName);
                    if (configurationTable != null && !configurationTable.isEmpty()) {
                        for (String pid : configurationTable.keySet()) {
                            if (delegate.getStrings() != null && !delegate.getStrings().contains(pid)) {
                                delegate.getStrings().add(pid);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            // Ignore
        }
        return delegate.complete(buffer, cursor, candidates);
    }

    public ClusterManager getClusterManager() {
        return clusterManager;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }
}
