package net.cellar.shell.group;

import net.cellar.core.control.ManageGroupAction;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "group-list", description = "Manages nodes and groups")
public class GroupListCommand extends GroupSupport {

    @Argument(index = 0, name = "node", description = "The id of the node(s) to turn on/off event producer", required = false, multiValued = true)
    List<String> nodes;

    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        return doExecute(ManageGroupAction.LIST, null, nodes);
    }
}
