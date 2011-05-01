package net.cellar.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import net.cellar.core.Group;
import net.cellar.core.GroupManager;
import net.cellar.core.Synchronizer;

import java.util.List;
import java.util.Set;

/**
 * @author: iocanel
 */
public class CellarMembershipListener implements MembershipListener {

    private GroupManager groupManager;
    private HazelcastInstance instance;
    private List<? extends Synchronizer> synchronizers;

    public CellarMembershipListener(HazelcastInstance instance) {
        this.instance = instance;
        instance.getCluster().addMembershipListener(this);
    }

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        Member member = membershipEvent.getMember();
        Member local = instance.getCluster().getLocalMember();

        if (local.equals(member)) {
            if (synchronizers != null && !synchronizers.isEmpty()) {
                Set<Group> groups = groupManager.listLocalGroups();
                if (groups != null && !groups.isEmpty()) {
                    for (Group group : groups) {
                        for (Synchronizer synchronizer : synchronizers) {
                            if (synchronizer.isSyncEnabled(group)) {
                                synchronizer.pull(group);
                                synchronizer.push(group);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {

    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public List<? extends Synchronizer> getSynchronizers() {
        return synchronizers;
    }

    public void setSynchronizers(List<? extends Synchronizer> synchronizers) {
        this.synchronizers = synchronizers;
    }
}
