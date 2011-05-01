package net.cellar.shell.group;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "group-create", description = "Creates an empty group")
public class GroupCreateCommand extends GroupSupport {


    @Argument(index = 0, name = "group", description = "The name of the group to join", required = false, multiValued = false)
    String group;


    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        return groupManager.createGroup(group);
    }
}
