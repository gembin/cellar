package net.cellar.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: iocanel
 */
public class Group implements Serializable {

    private String name;
    private Set<Node> members = new HashSet<Node>();


    /**
     * Constructor
     *
     * @param name
     */
    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Node> getMembers() {
        return members;
    }

    public void setMembers(Set<Node> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group that = (Group) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
