package net.cellar.shell.producer;

import net.cellar.core.control.SwitchStatus;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "producer-start", description = "Turns on the producer capabilities of a node.")
public class ProducerStartCommand extends ProducerSupport {

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
        return doExecute(nodes, SwitchStatus.ON);
    }
}
