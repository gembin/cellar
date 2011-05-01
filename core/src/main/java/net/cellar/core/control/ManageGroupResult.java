package net.cellar.core.control;

import net.cellar.core.Group;
import net.cellar.core.command.Result;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: iocanel
 */
public class ManageGroupResult extends Result {

    private Boolean success = Boolean.TRUE;
    private Set<Group> groups = new HashSet<Group>();

    public ManageGroupResult(String id) {
        super(id);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
