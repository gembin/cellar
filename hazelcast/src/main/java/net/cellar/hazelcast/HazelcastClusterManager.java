package net.cellar.hazelcast;



import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import com.hazelcast.core.Member;
import net.cellar.core.ClusterManager;
import net.cellar.core.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author iocanel
 */
public class HazelcastClusterManager implements ClusterManager {

	private static final String GENERATOR_ID = "net.cellar.idgen";

	private HazelcastInstance instance;
	private IdGenerator idgenerator;

	/**
	 * Returns a named distributed map.
	 * @param mapName
	 * @return
	 */
	public Map getMap(String mapName) {
		return instance.getMap(mapName);
	}

	/**
	 * Returns a named distributed list.
	 * @param listName
	 * @return
	 */
	public List getList(String listName) {
		return instance.getList(listName);
	}

	/**
	 * Returns the list of Hazelcast Nodes.
	 * @return
	 */
	public List<Node> getNodes() {
		List<Node> nodes = new ArrayList<Node>();

		Cluster cluster = instance.getCluster();
		if(cluster != null) {
			Set<Member> members = cluster.getMembers();
			if(members!=null && !members.isEmpty()) {
				for(Member member:members) {
					HazelcastNode node = new HazelcastNode(member.getInetSocketAddress().getHostName(),member.getInetSocketAddress().getPort());
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	/**
	 * Returns the node on which the command was run.
	 * @return
	 */
	public Node getNode() {
		Cluster cluster = instance.getCluster();
		if(cluster != null) {
		   Member member = cluster.getLocalMember();
		   HazelcastNode node = new HazelcastNode(member.getInetSocketAddress().getHostName(),member.getInetSocketAddress().getPort());
		   return node;
		}
		else return null;
	}

	/**
	 * Returns the {@code Node}s with the corresponding ids.
	 * @param ids
	 * @return
	 */
	public List<Node> getNode(List<String> ids) {
		List<Node> nodes = new ArrayList<Node>();
		if (ids != null && !ids.isEmpty()) {
			Cluster cluster = instance.getCluster();
			if (cluster != null) {
				Set<Member> members = cluster.getMembers();
				if (members != null && !members.isEmpty()) {
					for (Member member : members) {
						HazelcastNode node = new HazelcastNode(member.getInetSocketAddress().getHostName(), member.getInetSocketAddress().getPort());
						if (ids.contains(node.getId())) {
							nodes.add(node);
						}
					}
				}
			}
		}
		return nodes;
	}

	/**
	 * Returns the {@code Node} with the corresponding id.
	 *
	 * @param id
	 * @return
	 */
	public Node getNode(String id) {
		if (id != null) {
			Cluster cluster = instance.getCluster();
			if (cluster != null) {
				Set<Member> members = cluster.getMembers();
				if (members != null && !members.isEmpty()) {
					for (Member member : members) {
						HazelcastNode node = new HazelcastNode(member.getInetSocketAddress().getHostName(), member.getInetSocketAddress().getPort());
						if (id.equals(node.getId())) {
							return node;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Generate an id.
	 * @return
	 */
	public synchronized String generateId() {
		if(idgenerator == null) {
           idgenerator = instance.getIdGenerator(GENERATOR_ID);
		}
		return String.valueOf(idgenerator.newId());
	}

	public void start() {

	}

	public void stop() {
		instance.shutdown();
	}

	public void restart() {
		instance.restart();
	}

	/**
	 * Returns the Hazelcast instance.
	 * @return
	 */
	public HazelcastInstance getInstance() {
		return instance;
	}

	/**
	 * Sets the Hazelcast instance.
	 * @param instance
	 */
	public void setInstance(HazelcastInstance instance) {
		this.instance = instance;
	}
}
