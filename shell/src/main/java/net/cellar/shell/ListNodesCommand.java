package net.cellar.shell;

import net.cellar.core.Node;
import org.apache.felix.gogo.commands.Command;

import java.util.Set;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "list-nodes", description = "Lists the nodes of the cluster.")
public class ListNodesCommand extends ClusterCommandSuppot {

    private static final String LIST_FORMAT = "%1s %4s %-20s %5s %s";

    @Override
    protected Object doExecute() throws Exception {
        if (clusterManager == null) {
            System.err.println("Cluster Manager not found!");
            return null;
        } else {
            Set<Node> nodes = clusterManager.listNodes();
            if (nodes != null && !nodes.isEmpty()) {
                int count = 1;
                System.out.println(String.format(LIST_FORMAT, " ", "No.", "Host Name", "Port", "ID"));
                for (Node node : nodes) {
                    String mark = " ";
                    if (node.equals(clusterManager.getNode()))
                        mark = "*";
                    System.out.println(String.format(LIST_FORMAT, mark, count++, node.getHost(), node.getPort(), node.getId()));
                }
            } else {
                System.err.println("No node found in the cluster!");
                return null;
            }
        }
        return null;
    }

}
