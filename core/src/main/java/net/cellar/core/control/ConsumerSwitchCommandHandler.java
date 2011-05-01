package net.cellar.core.control;

import net.cellar.core.Consumer;
import net.cellar.core.command.CommandHandler;

/**
 * @author iocanel
 */
public class ConsumerSwitchCommandHandler extends CommandHandler<ConsumerSwitchCommand, ConsumerSwitchResult> {

    public static final String SWITCH_ID = "net.cellar.command.producer.switch";
    private final Switch commandSwitch = new BasicSwitch(SWITCH_ID);

    private Consumer consumer;

    /**
     * Handle the {@code ProducerSwitchCommand} command.
     *
     * @param command
     */
    public ConsumerSwitchResult execute(ConsumerSwitchCommand command) {
        //Query
        if (command.getStatus() == null) {
            ConsumerSwitchResult result = new ConsumerSwitchResult(command.getId(), Boolean.TRUE, consumer.getSwitch().getStatus().getValue());
            return result;
        }
        //Turn on the switch
        if (command.getStatus().equals(SwitchStatus.ON)) {
            consumer.getSwitch().turnOn();
            ConsumerSwitchResult result = new ConsumerSwitchResult(command.getId(), Boolean.TRUE, Boolean.TRUE);
            return result;
        }
        //Turn on the switch
        else if (command.getStatus().equals(SwitchStatus.OFF)) {
            consumer.getSwitch().turnOff();
            ConsumerSwitchResult result = new ConsumerSwitchResult(command.getId(), Boolean.TRUE, Boolean.FALSE);
            return result;
        } else {
            ConsumerSwitchResult result = new ConsumerSwitchResult(command.getId(), Boolean.FALSE, consumer.getSwitch().getStatus().getValue());
            return result;
        }
    }

    public Class<ConsumerSwitchCommand> getType() {
        return ConsumerSwitchCommand.class;
    }

    public Switch getSwitch() {
        return commandSwitch;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
