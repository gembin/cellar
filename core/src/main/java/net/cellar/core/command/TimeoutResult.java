package net.cellar.core.command;

/**
 * A result that is used to denote that no result has been received during the timeout.
 * @author iocanel
 */
public class TimeoutResult extends Result {

	public TimeoutResult(String id) {
		super(id);
	}
}
