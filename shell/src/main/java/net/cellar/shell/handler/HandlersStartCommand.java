package net.cellar.shell.handler;

import net.cellar.core.ClusterManager;
import net.cellar.core.command.ExecutionContext;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "handlers", description = "Starts the handler of the specified nodes.")
public class HandlersStartCommand extends HandlersSupport {

    private static final String OUTPUT_FORMAT = "%-20s %-7s %s";

    private ClusterManager clusterManager;
    private ExecutionContext executionContext;

    @Argument(index = 0, name = "handler-start", description = "The id of the event handler", required = false, multiValued = false)
    String handler;

    @Argument(index = 1, name = "node", description = "The id of the node(s)", required = false, multiValued = true)
    List<String> nodes;


    /**
     * Execute the command.
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Object doExecute() throws Exception {
        return doExecute(handler, nodes, Boolean.TRUE);
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
