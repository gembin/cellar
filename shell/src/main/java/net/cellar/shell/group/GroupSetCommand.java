package net.cellar.shell.group;

import net.cellar.core.control.ManageGroupAction;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.List;

/**
 * @author: iocanel
 */
@Command(scope = "cluster", name = "group-set", description = "Set the target nodes to a specific group")
public class GroupSetCommand extends GroupSupport {


    @Argument(index = 0, name = "group", description = "The name of the group to join", required = false, multiValued = false)
    String group;

    @Argument(index = 1, name = "node", description = "The id of the node(s) to turn on/off event producer. If none specified current is assumed", required = false, multiValued = true)
    List<String> nodes;

    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        return doExecute(ManageGroupAction.SET, group, nodes);
    }
}
