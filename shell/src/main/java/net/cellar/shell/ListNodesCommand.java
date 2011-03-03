package net.cellar.shell;

import net.cellar.core.ClusterManager;
import net.cellar.core.Node;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "list", description = "Lists the nodes of the cluster.")
public class ListNodesCommand extends OsgiCommandSupport {

	private static final String LIST_FORMAT = "%4s %-20s %5s %s";

	private ClusterManager clusterManager;

	@Override
	protected Object doExecute() throws Exception {
		if(clusterManager == null) {
			System.err.println("Cluster Manager not found!");
			return null;
		} else {
			List<Node> nodes = clusterManager.getNodes();
			if(nodes != null && !nodes.isEmpty()) {
			  int count = 1;
			  System.out.println(String.format(LIST_FORMAT,"No. ","Host Name","Port","ID"));
			  for(Node node:nodes) {
                  System.out.println(String.format(LIST_FORMAT,count++,node.getHost(),node.getPort(),node.getId()));
			  }
			} else {
			System.err.println("No node found in the cluster!");
			return null;
			}
		}
		return null;
	}

	public ClusterManager getClusterManager() {
		return clusterManager;
	}

	public void setClusterManager(ClusterManager clusterManager) {
		this.clusterManager = clusterManager;
	}
}
