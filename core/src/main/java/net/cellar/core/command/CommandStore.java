package net.cellar.core.command;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author iocanel
 */
public class CommandStore {

	private ConcurrentMap<String, Command> pending = new ConcurrentHashMap<String, Command>();

	public ConcurrentMap<String, Command> getPending() {
		return pending;
	}

	public void setPending(ConcurrentMap<String, Command> pending) {
		this.pending = pending;
	}
}
