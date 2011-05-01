package net.cellar.core.shell.completers;

import net.cellar.core.Group;

/**
 * @author iocanel
 */
public class AllGroupsCompleter extends GroupCompleterSupport {
    @Override
    protected boolean acceptsGroup(Group group) {
        return true;
    }
}
