package net.cellar.core.command;

import java.util.concurrent.ConcurrentMap;

/**
 * @author iocanel
 */
public interface CommandStore {

    public ConcurrentMap<String, Command> getPending();
	public void setPending(ConcurrentMap<String, Command> pending);
}
