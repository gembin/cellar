package net.cellar.shell.consumer;


import net.cellar.core.control.SwitchStatus;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "consumer-stop", description = "Turns on/off the producer capabilities of a node.")
public class ConsumerStopCommand extends ConsumerSupport {

    @Argument(index = 0, name = "node", description = "The id of the node(s) to turn on/off event producer", required = false, multiValued = true)
    List<String> nodes;

    /**
     * Execut the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        return doExecute(nodes, SwitchStatus.OFF);
    }
}
