package net.cellar.core;

import java.util.List;
import java.util.Map;

/**
 * @author iocanel
 */
public interface ClusterManager {

	/**
	 * Returns a named distributed map.
	 * @param mapName
	 * @return
	 */
	public Map getMap(String mapName);

	/**
	 * Returns a named distributed list.
	 * @param listName
	 * @return
	 */
	public List getList(String listName);

	/**
	 * Returns a list of {@code Node}s.
	 * @return
	 */
	public List<Node> getNodes();

	/**
	 * Returns the node with specified id.
	 * @return
	 */
	public List<Node> getNode(List<String> ids);


	/**
	 * Returns the node with specified id.
	 * @return
	 */
	public Node getNode(String id);


	/**
	 * Returns the {@code Node} on which the command was executed.
	 * @return
	 */
	public Node getNode();

	/**
	 * Generate an Id unique accross the cluster.
	 * @return
	 */
	public String generateId();

	/**
	 * Start the clustering support on the node.
	 */
	public void start();

	/**
	 * Stops the node.
	 */
	public void stop();

	/**
	 * Restarts the clustering support on the node.
	 */
	public void restart();
}
