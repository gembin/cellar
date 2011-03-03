package net.cellar.core.command;

import net.cellar.core.Node;
import net.cellar.core.Producer;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author iocanel
 */
public class ClusteredExecutionContext implements ExecutionContext {

	private Producer producer;
	private CommandStore commandStore;

	private ScheduledExecutorService timeoutScheduler = new ScheduledThreadPoolExecutor(10);

	public <R extends Result, C extends Command<R>> Map<Node,R> execute(C command) throws Exception {
		if (command == null) {
			throw new Exception("Command store not found");
		} else {
			commandStore.getPending().put(command.getId(), command);
			TimeoutTask timeoutTask = new TimeoutTask(command, commandStore);
			ScheduledFuture<?> timeoutFuture = timeoutScheduler.schedule(timeoutTask, command.getTimeout(), TimeUnit.MILLISECONDS);
		}

		if (producer != null) {
			producer.produce(command);
			return command.getResult();
		}
		throw new Exception("Command producer not found");
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public CommandStore getCommandStore() {
		return commandStore;
	}

	public void setCommandStore(CommandStore commandStore) {
		this.commandStore = commandStore;
	}
}
