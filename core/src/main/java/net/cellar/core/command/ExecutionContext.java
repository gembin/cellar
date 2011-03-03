package net.cellar.core.command;

import net.cellar.core.Node;

import java.util.Map;

/**
 * Command Execution Context.
 * @author iocanel
 */
public interface ExecutionContext {

	/**
	 * Execute {@link Command} and retrieve {@link Result}.
	 * @param command
	 * @param <R>
	 * @param <C>
	 * @return
	 * @throws Exception
	 */
	public <R extends Result, C extends Command<R>> Map<Node,R> execute(C command) throws Exception;
}
