package net.cellar.hazelcast;


import net.cellar.core.Node;

/**
 * @author iocanel
 */
public class HazelcastNode implements Node {

	private String id;

	private String host;
    private int port;

	/**
	 * Cosntructor
	 * @param host
	 * @param port
	 */
	public HazelcastNode(String host, int port) {
		StringBuilder builder = new StringBuilder();
		this.host = host;
		this.port = port;
		this.id = builder.append(host).append(":").append(port).toString();
	}

	public String getId() {
		return id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HazelcastNode that = (HazelcastNode) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
