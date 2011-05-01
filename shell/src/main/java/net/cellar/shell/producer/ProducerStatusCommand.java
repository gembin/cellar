package net.cellar.shell.producer;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "producer-start", description = "Displays the producer capabilities of a node.")
public class ProducerStatusCommand extends ProducerSupport {

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
        return doExecute(nodes, null);
    }
}
