package net.cellar.shell;

import net.cellar.core.ClusterManager;
import net.cellar.core.Node;
import net.cellar.core.command.ExecutionContext;
import net.cellar.utils.ping.Ping;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import java.util.Arrays;
import java.util.List;

/**
 * @author iocanel
 */
@Command(scope = "cluster", name = "ping", description = "Pings the nodes of the cluster.")
public class PingCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "node", description = "The id of the node(s) to ping", required = true, multiValued = false)
    String nodeId;

    @Argument(index = 1, name = "iterations", description = "The number of iterations to perform", required = false, multiValued = false)
    Integer iterations=10;

    @Argument(index = 2, name = "interval", description = "The time in millis to wait between iterations", required = false, multiValued = false)
    Long interval=1000L;

    private ClusterManager clusterManager;
    private ExecutionContext executionContext;

    @Override
    protected Object doExecute() throws Exception {
        Node node = clusterManager.getNode(nodeId);
        System.out.println("Pinging node :"+node.getId());
        for(int i=1;i<=iterations;i++) {
            Long start = System.currentTimeMillis();
            Ping ping = new Ping(clusterManager.generateId());
            ping.setDestination(Arrays.asList(node));
            executionContext.execute(ping);
            Long stop = System.currentTimeMillis();
            Long delay = stop - start;
            System.out.println(String.format("PING %s %s %sms",i,node.getId(),delay));
            Thread.sleep(interval);
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
