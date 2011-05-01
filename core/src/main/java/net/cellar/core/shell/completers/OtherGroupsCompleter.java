package net.cellar.core.shell.completers;

import net.cellar.core.Group;
import net.cellar.core.Node;

/**
 * @author: iocanel
 */
public class OtherGroupsCompleter extends GroupCompleterSupport {
    @Override
    protected boolean acceptsGroup(Group group) {
        Node node = groupManager.getNode();
        if (group.getMembers().contains(node))
            return false;
        else return true;
    }
}
