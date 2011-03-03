package net.cellar.core.command;

import net.cellar.core.Node;
import net.cellar.core.Producer;
import net.cellar.core.control.Switch;
import net.cellar.core.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iocanel
 */
public abstract class CommandHandler<C extends Command<R>,R extends Result> implements EventHandler<C> {

	protected Producer producer;

	/**
	 * Hanldes the the {@code Command}.
	 * @param command
	 */
	public void handle(C command) {
		if(producer != null) {
			R result = execute(command);

			List<Node> destination = new ArrayList<Node>();
			destination.add(command.getSource());

			result.setDestination(destination);
			producer.produce(result);
		}
	}

	/**
	 * Executes a {@code Command} and returns a {@code Result}.
	 * @param command
	 * @return
	 */
	public abstract R execute(C command);

	public abstract Class<C> getType();

	public abstract Switch getSwitch();

	/**
	 * Returns the {@code Producer}.
	 * @return
	 */
	public Producer getProducer() {
		return producer;
	}

	/**
	 * Sets the {@code Producer}.
	 * @param producer
	 */
	public void setProducer(Producer producer) {
		this.producer = producer;
	}
}
