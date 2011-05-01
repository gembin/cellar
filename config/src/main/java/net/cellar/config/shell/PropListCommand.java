package net.cellar.config.shell;

import net.cellar.config.Constants;
import net.cellar.core.Configurations;
import net.cellar.core.shell.CellarCommandSupport;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;

import java.util.Map;
import java.util.Properties;

/**
 * @author: iocanel
 */
@Command(scope = "cluster", name = "config-proplist", description = "List the config pids that are assigned to a group")
public class PropListCommand extends CellarCommandSupport {

    protected static final String OUTPUT_FORMAT = "%-40s %s";

    @Argument(index = 0, name = "group", description = "The name of the group", required = true, multiValued = false)
    String groupName;

    @Argument(index = 1, name = "pid", description = "The pid of the configuration", required = true, multiValued = false)
    String pid;

    @Override
    protected Object doExecute() throws Exception {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

            Map<String, Properties> configurationTable = clusterManager.getMap(Constants.CONFIGURATION_MAP + Configurations.SEPARATOR + groupName);

            if (configurationTable != null && !configurationTable.isEmpty()) {
                System.out.println(String.format("Property list for PID:" + pid + " for group:" + groupName));
                System.out.println(String.format(OUTPUT_FORMAT, "Key", "Value"));
                Properties properties = configurationTable.get(pid);
                if (properties != null && !properties.isEmpty())
                    for (Object key : properties.keySet()) {
                        String value = properties.getProperty((String) key);
                        System.out.println(String.format(OUTPUT_FORMAT, key, value));
                    }
            } else System.err.print("No PIDs found for group:" + groupName);
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);

        }
        return null;
    }
}
