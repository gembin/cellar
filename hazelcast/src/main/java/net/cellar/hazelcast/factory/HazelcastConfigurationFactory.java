package net.cellar.hazelcast.factory;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;


/**
 * @author: iocanel
 */
public class HazelcastConfigurationFactory {

    public static Config build(String name, String password) {
        Config config = new Config();
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setName(name);
        groupConfig.setPassword(password);
        config.setGroupConfig(groupConfig);
        return config;
    }
}
