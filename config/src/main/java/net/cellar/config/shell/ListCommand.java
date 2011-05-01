package net.cellar.config.shell;

import net.cellar.config.Constants;
import net.cellar.core.Configurations;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.Map;
import java.util.Properties;

/**
 * @author: iocanel
 */
@Command(scope = "cluster", name = "config-list", description = "List the config pids that are assigned to a group")
public class ListCommand extends ConfigCommandSupport {

    protected static final String OUTPUT_FORMAT = "%-40s";

    @Argument(index = 0, name = "group", description = "The name of the group", required = true, multiValued = false)
    String groupName;

    @Override
    protected Object doExecute() throws Exception {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

            Map<String, Properties> configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP + Configurations.SEPARATOR + groupName);

            if (configurationTable != null && !configurationTable.isEmpty()) {
                System.out.println(String.format("PIDs for group:" + groupName));
                System.out.println(String.format(OUTPUT_FORMAT, "PID"));
                for (String pid : configurationTable.keySet()) {
                    System.out.println(String.format(OUTPUT_FORMAT, pid));
                }
            } else System.err.print("No PIDs found for group:" + groupName);
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);

        }
        return null;
    }
}
