package net.cellar.shell;

import net.cellar.core.ClusterManager;
import net.cellar.core.Node;
import net.cellar.core.command.ExecutionContext;
import net.cellar.core.control.ManageHandlersCommand;
import net.cellar.core.control.ManageHandlersResult;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import java.util.List;
import java.util.Map;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "handlers", description = "Lists the handlers of the specified nodes.")
public class HandlersCommand extends OsgiCommandSupport {

    private static final String OUTPUT_FORMAT = "%-20s %-7s %s";

	private ClusterManager clusterManager;
	private ExecutionContext executionContext;

	@Argument(index = 0, name = "node", description = "The id of the node(s) to turn on/off event producer", required = false, multiValued = true)
    List<String> nodes;

	@Option(name = "-on", aliases = "--turn-on", description = "Turns on event producer", required = false, multiValued = false)
    boolean on;

	@Option(name = "-off", aliases = "--turn-off", description = "Turns off event producer", required = false, multiValued = false)
    boolean off;

    @Option(name = "-h", aliases = "--handler", description = "The class name of the handler.", required = false, multiValued = true)
    String handler;

    /**
     * Execut the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        ManageHandlersCommand command = new ManageHandlersCommand(clusterManager.generateId());
        List<Node> recipientList = clusterManager.getNode(nodes);

        //Set the recipient list
        if (recipientList != null && !recipientList.isEmpty()) {
            command.setDestination(recipientList);
        }

        //Set the name of the handler.
        if (handler != null && handler.length() > 2) {
            handler = handler.substring(1);
            handler = handler.substring(0,handler.length() -1);
            command.setHandlesName(handler);
            if (on) {
                command.setStatus(true);
            } else if (off) {
                command.setStatus(false);
            }
        }


        executionContext.execute(command);

        Map<Node, ManageHandlersResult> results = command.getResult();
        if (results == null || results.isEmpty()) {
            System.out.println("No result received within given timeout");
        } else {
            System.out.println(String.format(OUTPUT_FORMAT, "Node","Status", "Event Handler" ));
            for (Node node : results.keySet()) {
                ManageHandlersResult result = results.get(node);
                if (result != null && result.getHandlers() != null) {
                    for(String handler : result.getHandlers().keySet()) {
                       String status = result.getHandlers().get(handler);
                       System.out.println(String.format(OUTPUT_FORMAT, node.getId(),status, handler));
                    }
                }
            }
        }
        return null;
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public ClusterManager getClusterManager() {
        return clusterManager;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }
}
