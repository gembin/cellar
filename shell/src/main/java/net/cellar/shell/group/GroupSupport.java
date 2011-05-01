package net.cellar.shell.group;

import net.cellar.core.Group;
import net.cellar.core.Node;
import net.cellar.core.control.ManageGroupAction;
import net.cellar.core.control.ManageGroupCommand;
import net.cellar.core.control.ManageGroupResult;
import net.cellar.shell.ClusterCommandSuppot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author iocanel
 */
public abstract class GroupSupport extends ClusterCommandSuppot {

    protected static final String OUTPUT_FORMAT = "%1s %-20s %s";

    /**
     * Execut the command.
     *
     * @return
     * @throws Exception
     */
    protected Object doExecute(ManageGroupAction action, String group, Collection<String> nodes) throws Exception {
        ManageGroupCommand command = new ManageGroupCommand(clusterManager.generateId());
        Set<Node> recipientList = clusterManager.listNodes(nodes);

        //Set the recipient list
        if (recipientList != null && !recipientList.isEmpty()) {
            command.setDestination(recipientList);
        } else {
            Set<Node> recipients = new HashSet<Node>();
            recipients.add(clusterManager.getNode());
            command.setDestination(recipients);
        }

        command.setAction(action);


        if (group != null) {
            command.setGroupName(group);
        }

        Map<Node, ManageGroupResult> results = executionContext.execute(command);
        if (results == null || results.isEmpty()) {
            System.out.println("No result received within given timeout");
        } else {
            System.out.println(String.format(OUTPUT_FORMAT, " ", "Node", "Group"));
            for (Node node : results.keySet()) {
                ManageGroupResult result = results.get(node);
                if (result != null && result.getGroups() != null) {
                    for (Group g : result.getGroups()) {
                        if (g.getMembers() != null && !g.getMembers().isEmpty()) {
                            for (Node memeber : g.getMembers()) {
                                String name = g.getName();
                                String mark = " ";
                                if (memeber.equals(clusterManager.getNode()))
                                    mark = "*";
                                System.out.println(String.format(OUTPUT_FORMAT, mark, memeber.getId(), name));
                            }
                        } else System.out.println(String.format(OUTPUT_FORMAT, "", "", g.getName()));
                    }
                }
            }
        }
        return null;
    }
}
