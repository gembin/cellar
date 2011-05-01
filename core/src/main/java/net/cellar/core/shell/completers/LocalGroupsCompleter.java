package net.cellar.core.shell.completers;

import net.cellar.core.Group;
import net.cellar.core.Node;

/**
 * @author: iocanel
 */
public class LocalGroupsCompleter extends GroupCompleterSupport {
    @Override
    protected boolean acceptsGroup(Group group) {
        Node node = groupManager.getNode();
        if (group.getMembers().contains(node))
            return true;
        else return false;
    }
}
