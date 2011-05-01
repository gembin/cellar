package net.cellar.shell.group;

import net.cellar.core.Group;
import net.cellar.core.Node;
import net.cellar.core.control.ManageGroupAction;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.LinkedList;
import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "group-delete", description = "Deletes a group")
public class GroupDeleteCommand extends GroupSupport {


    @Argument(index = 0, name = "group", description = "The name of the group to delete", required = false, multiValued = false)
    String group;

    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            Group g = groupManager.findGroupByName(group);
            List<String> nodes = new LinkedList<String>();

            if (g.getMembers() != null && !g.getMembers().isEmpty()) {
                for (Node n : g.getMembers()) {
                    nodes.add(n.getId());
                }
                doExecute(ManageGroupAction.QUIT, group, nodes);
            }

            groupManager.deleteGroup(group);
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
        return null;
    }
}
