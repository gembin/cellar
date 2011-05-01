package net.cellar.shell.consumer;


import net.cellar.core.Node;
import net.cellar.core.control.ConsumerSwitchCommand;
import net.cellar.core.control.ConsumerSwitchResult;
import net.cellar.core.control.SwitchStatus;
import net.cellar.shell.ClusterCommandSuppot;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author iocanel
 */

public abstract class ConsumerSupport extends ClusterCommandSuppot {

    protected static final String OUTPUT_FORMAT = "%-20s %s";

    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    protected Object doExecute(List<String> nodes, SwitchStatus status) throws Exception {
        ConsumerSwitchCommand command = new ConsumerSwitchCommand(clusterManager.generateId());
        Set<Node> recipientList = clusterManager.listNodes(nodes);

        if (recipientList != null && !recipientList.isEmpty()) {
            command.setDestination(recipientList);
        }

        command.setStatus(status);

        Map<Node, ConsumerSwitchResult> results = executionContext.execute(command);
        if (results == null || results.isEmpty()) {
            System.out.println("No result received within given timeout");
        } else {
            System.out.println(String.format(OUTPUT_FORMAT, "Node", "Status"));
            for (Node node : results.keySet()) {
                ConsumerSwitchResult result = results.get(node);
                System.out.println(String.format(OUTPUT_FORMAT, node.getId(), result.getStatus()));
            }
        }
        return null;
    }
}
