package net.cellar.shell.handler;

import net.cellar.core.Node;
import net.cellar.core.control.ManageHandlersCommand;
import net.cellar.core.control.ManageHandlersResult;
import net.cellar.shell.ClusterCommandSuppot;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author iocanel
 */
public abstract class HandlersSupport extends ClusterCommandSuppot {

    protected static final String OUTPUT_FORMAT = "%-20s %-7s %s";

    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    protected Object doExecute(String handler, List<String> nodes, Boolean status) throws Exception {
        ManageHandlersCommand command = new ManageHandlersCommand(clusterManager.generateId());
        Set<Node> recipientList = clusterManager.listNodes(nodes);

        //Set the recipient list
        if (recipientList != null && !recipientList.isEmpty()) {
            command.setDestination(recipientList);
        }

        //Set the name of the handler.
        if (handler != null && handler.length() > 2) {
            handler = handler.substring(1);
            handler = handler.substring(0, handler.length() - 1);
            command.setHandlesName(handler);
        }

        command.setStatus(status);


        Map<Node, ManageHandlersResult> results = executionContext.execute(command);
        if (results == null || results.isEmpty()) {
            System.out.println("No result received within given timeout");
        } else {
            System.out.println(String.format(OUTPUT_FORMAT, "Node", "Status", "Event Handler"));
            for (Node node : results.keySet()) {
                ManageHandlersResult result = results.get(node);
                if (result != null && result.getHandlers() != null) {
                    for (String h : result.getHandlers().keySet()) {
                        String s = result.getHandlers().get(h);
                        System.out.println(String.format(OUTPUT_FORMAT, node.getId(), s, handler));
                    }
                }
            }
        }
        return null;
    }
}
