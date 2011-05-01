package net.cellar.core.shell;

import net.cellar.core.ClusterManager;
import net.cellar.core.GroupManager;
import org.apache.karaf.shell.console.OsgiCommandSupport;

/**
 * @author: iocanel
 */
public abstract class CellarCommandSupport extends OsgiCommandSupport {

    protected ClusterManager clusterManager;
    protected GroupManager groupManager;


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
