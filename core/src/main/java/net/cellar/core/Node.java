package net.cellar.core;

import java.io.Serializable;

/**
 * @author iocanel
 */
public interface Node extends Serializable {

	/**
	 * Returns the identifier of the node.
	 * @return
	 */
	public String getId();

	/**
	 * Returns the name of the host
	 * @return
	 */
	 public String getHost();

	/**
	 * Returns the Port of the host
	 * @return
	 */
	 public int getPort();
}
