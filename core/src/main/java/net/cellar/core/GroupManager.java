package net.cellar.core;

import java.util.Map;
import java.util.Set;

/**
 * @author: iocanel
 */
public interface GroupManager {

    /**
     * Returns the local {@link Node}.
     *
     * @return
     */
    public Node getNode();

    /**
     * Creates {@link Group}
     *
     * @param groupName
     * @return
     */
    public Group createGroup(String groupName);

    /**
     * Deletes {@link Group}
     *
     * @param groupName
     */
    public void deleteGroup(String groupName);

    /**
     * Return the {@link Group} by name.
     *
     * @param groupName
     * @return
     */
    public Group findGroupByName(String groupName);

    /**
     * Returns a map of {@link Node}s.
     *
     * @return
     */
    public Map<String, Group> listGroups();

    /**
     * Returns the {@link Group}s of the specified {@link Node}.
     *
     * @return
     */
    public Set<Group> listLocalGroups();

    /**
     * Returns the {@link Group}s of the specified {@link Node}.
     *
     * @return
     */
    public Set<Group> listAllGroups();


    /**
     * Returns the {@link Group}s of the specified {@link Node}.
     *
     * @param node
     * @return
     */
    public Set<Group> listGroups(Node node);

    /**
     * Retrurns the {@link Group} names of the current {@Node}.
     *
     * @return
     */
    public Set<String> listGroupNames();

    /**
     * Returns the {@link Group} names of the specified {@link Node}.
     *
     * @param node
     * @return
     */
    public Set<String> listGroupNames(Node node);


    /**
     * Registers current {@link Node} to the {@link Group}.
     *
     * @param group
     */
    public void registerGroup(Group group);

    /**
     * Registers current {@link Node} to the {@link Group}.
     *
     * @param groupName
     */
    public void registerGroup(String groupName);


    /**
     * UnRegisters current {@link Node} to the {@link Group}.
     *
     * @param group
     */
    public void unRegisterGroup(Group group);

    /**
     * UnRegisters current {@link Node} to the {@link Group}.
     *
     * @param groupName
     */
    public void unRegisterGroup(String groupName);

}
