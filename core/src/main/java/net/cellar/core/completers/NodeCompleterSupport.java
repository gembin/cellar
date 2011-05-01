package net.cellar.core.completers;

import net.cellar.core.ClusterManager;
import net.cellar.core.Node;
import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;

import java.util.List;

/**
 * @author iocanel
 */
public abstract class NodeCompleterSupport implements Completer {

    private ClusterManager clusterManager;

    public int complete(String buffer, int cursor, List<String> candidates) {
        StringsCompleter delegate = new StringsCompleter();
        try {
            for (Node node : clusterManager.listNodes()) {
                if (acceptsNode(node)) {
                    String id = node.getId();
                    if (delegate.getStrings() != null && !delegate.getStrings().contains(id)) {
                        delegate.getStrings().add(id);
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return delegate.complete(buffer, cursor, candidates);
    }

    protected abstract boolean acceptsNode(Node node);

    public ClusterManager getClusterManager() {
        return clusterManager;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }
}
