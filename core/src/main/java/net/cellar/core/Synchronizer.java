package net.cellar.core;

/**
 * @author: iocanel
 */
public interface Synchronizer {

    /**
     * Pushes local state to the shared resource.
     */
    public void push(Group group);

    /**
     * Pull state changes from the shared resource.
     */
    public void pull(Group group);

    /**
     * Returns true if synchronization is enabled.
     *
     * @return
     */
    public Boolean isSyncEnabled(Group group);
}
