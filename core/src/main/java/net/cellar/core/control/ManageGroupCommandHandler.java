package net.cellar.core.control;

import net.cellar.core.Configurations;
import net.cellar.core.Group;
import net.cellar.core.Node;
import net.cellar.core.command.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author: iocanel
 */
public class ManageGroupCommandHandler extends CommandHandler<ManageGroupCommand, ManageGroupResult> {

    private static final Logger logger = LoggerFactory.getLogger(ManageGroupCommandHandler.class);

    public static final String SWITCH_ID = "net.cellar.command.managegroup.switch";
    private final Switch commandSwitch = new BasicSwitch(SWITCH_ID);


    @Override
    public ManageGroupResult execute(ManageGroupCommand command) {

        ManageGroupResult result = new ManageGroupResult(command.getId());
        ManageGroupAction action = command.getAction();

        String targetGroupName = command.getGroupName();
        Node node = clusterManager.getNode();

        if (ManageGroupAction.JOIN.equals(action)) {
            joinGroup(targetGroupName);
        } else if (ManageGroupAction.QUIT.equals(action)) {
            quitGroup(targetGroupName);
            if (groupManager.listLocalGroups().isEmpty()) {
                joinGroup(Configurations.DEFAULT_GROUP_NAME);
            }
        } else if (ManageGroupAction.PURGE.equals(action)) {
            purgeGroups();
            joinGroup(Configurations.DEFAULT_GROUP_NAME);
        } else if (ManageGroupAction.SET.equals(action)) {
            purgeGroups();
            joinGroup(targetGroupName);
        }

        if (ManageGroupAction.LIST.equals(action)) {
            addGrouListToResult(result, null);
        } else {
            addGrouListToResult(result, node);
        }

        return result;
    }

    /**
     * Adds the {@link Group} list to the result.
     *
     * @param result
     * @param node
     */
    public void addGrouListToResult(ManageGroupResult result, Node node) {
        Set<Group> groups = null;
        if (node != null) {
            groups = groupManager.listGroups(node);
        } else groups = groupManager.listAllGroups();

        for (Group g : groups) {
            if (g.getName() != null && !g.getName().isEmpty()) {
                result.getGroups().add(g);
            }
        }
    }

    /**
     * Adds {@link Node} to the target {@link Group}.
     *
     * @param targetGroupName
     */
    public void joinGroup(String targetGroupName) {
        Node node = clusterManager.getNode();
        Map<String, Group> groups = groupManager.listGroups();
        if (groups != null && !groups.isEmpty()) {
            Group targetGroup = groups.get(targetGroupName);
            if (targetGroup == null) {
                groupManager.registerGroup(targetGroupName);
            } else if (!targetGroup.getMembers().contains(node)) {
                targetGroup.getMembers().add(node);
                groupManager.listGroups().put(targetGroupName, targetGroup);
                groupManager.registerGroup(targetGroup);
            }
        }
    }

    /**
     * Removes {@link Node} from the target {@link Group}.
     *
     * @param targetGroupName
     */
    public void quitGroup(String targetGroupName) {
        Node node = clusterManager.getNode();
        Map<String, Group> groups = groupManager.listGroups();
        if (groups != null && !groups.isEmpty()) {
            Group targetGroup = groups.get(targetGroupName);
            if (targetGroup.getMembers().contains(node)) {
                targetGroup.getMembers().remove(node);
                groupManager.unRegisterGroup(targetGroup);
            }
        }
    }


    /**
     * Removes {@link Node} from ALL {@link Group}s.
     */
    public void purgeGroups() {
        Node node = clusterManager.getNode();
        Set<String> groupNames = groupManager.listGroupNames(node);
        if (groupNames != null && !groupNames.isEmpty()) {
            for (String targetGroupName : groupNames) {
                quitGroup(targetGroupName);
            }
        }
    }

    @Override
    public Class<ManageGroupCommand> getType() {
        return ManageGroupCommand.class;
    }

    @Override
    public Switch getSwitch() {
        return commandSwitch;
    }
}
