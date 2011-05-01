package net.cellar.core.completers;


import net.cellar.core.Node;

/**
 * A completer which includes all nodes.
 *
 * @author iocanel
 */
public class AllNodeCompleter extends NodeCompleterSupport {

    /**
     * Always returns true
     *
     * @param node
     * @return
     */
    @Override
    protected boolean acceptsNode(Node node) {
        return true;
    }
}
