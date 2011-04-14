package net.cellar.shell;


import net.cellar.core.ClusterManager;
import net.cellar.core.Node;
import net.cellar.core.command.ExecutionContext;
import net.cellar.core.control.ConsumerSwitchCommand;
import net.cellar.core.control.ConsumerSwitchResult;
import net.cellar.core.control.SwitchStatus;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import java.util.List;
import java.util.Map;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "consume", description = "Turns on/off the producer capabilities of a node.")
public class ConsumeCommand extends OsgiCommandSupport {

    private static final String OUTPUT_FORMAT = "%-20s %s";

    private ClusterManager clusterManager;
    private ExecutionContext executionContext;

    @Argument(index = 0, name = "node", description = "The id of the node(s) to turn on/off event producer", required = true, multiValued = true)
    List<String> nodes;

    @Option(name = "-on", aliases = "--turn-on", description = "Turns on event producer", required = false, multiValued = false)
    boolean on;

    @Option(name = "-off", aliases = "--turn-off", description = "Turns off event producer", required = false, multiValued = false)
    boolean off;

    /**
     * Execut the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        ConsumerSwitchCommand command = new ConsumerSwitchCommand(clusterManager.generateId());
        List<Node> recipientList = clusterManager.getNode(nodes);

        if (recipientList != null && !recipientList.isEmpty()) {
            command.setDestination(recipientList);
        }


        if (on) {
            command.setStatus(SwitchStatus.ON);
        } else if (off) {
            command.setStatus(SwitchStatus.OFF);
        } else {
            command.setStatus(null);
        }

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

    public ClusterManager getClusterManager() {
        return clusterManager;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }
}
