package net.cellar.core.command;

/**
 * A Runnable task that is used for scheduling command timeout events.
 * @author iocanel
 */
public class TimeoutTask implements Runnable {

	private Command command;
	private CommandStore store;

	public TimeoutTask(Command command, CommandStore store) {
		this.command = command;
		this.store = store;
	}

    /**
     * Runs the timeout task.
     */
    public void run() {
        //Check if command is still pending.
        Boolean pending = store.getPending().containsKey(command);
        if (pending) {
            store.getPending().remove(command);
        }
    }
}
