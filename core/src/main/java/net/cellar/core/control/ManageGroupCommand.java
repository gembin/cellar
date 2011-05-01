package net.cellar.core.control;

import net.cellar.core.command.Command;

/**
 * @author: iocanel
 */
public class ManageGroupCommand extends Command<ManageGroupResult> {

    private ManageGroupAction action;
    private String groupName;


    public ManageGroupCommand(String id) {
        super(id);
    }

    public ManageGroupAction getAction() {
        return action;
    }

    public void setAction(ManageGroupAction action) {
        this.action = action;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
