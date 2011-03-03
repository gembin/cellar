package net.cellar.core.control;

import net.cellar.core.Producer;
import net.cellar.core.command.CommandHandler;

/**
 * @author iocanel
 */
public class ProducerSwitchCommandHandler extends CommandHandler<ProducerSwitchCommand,ProducerSwitchResult> {

	public static final String SWITCH_ID = "net.cellar.command.producer.switch";
	private final Switch commandSwitch = new BasicSwitch(SWITCH_ID);


	/**
	 * Handle the {@code ProducerSwitchCommand} command.
	 * @param command
	 */
	public ProducerSwitchResult execute(ProducerSwitchCommand command) {
			//Turn on the switch
			if (command.getStatus().equals(SwitchStatus.ON)) {
				producer.getSwitch().turnOn();
				ProducerSwitchResult result = new ProducerSwitchResult(command.getId());
				return result;
			}
			//Turn on the switch
			else if (command.getStatus().equals(SwitchStatus.OFF)) {
				producer.getSwitch().turnOff();
				ProducerSwitchResult result = new ProducerSwitchResult(command.getId());
				return result;
			} else {
				ProducerSwitchResult result = new ProducerSwitchResult(command.getId(), Boolean.FALSE);
				return result;
			}
	}

	public Class<ProducerSwitchCommand> getType() {
		return  ProducerSwitchCommand.class;
	}

	public Switch getSwitch() {
		return commandSwitch;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}
}
