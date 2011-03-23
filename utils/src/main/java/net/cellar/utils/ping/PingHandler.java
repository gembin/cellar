package net.cellar.utils.ping;

import net.cellar.core.command.CommandHandler;
import net.cellar.core.control.BasicSwitch;
import net.cellar.core.control.Switch;

/**
 * @author iocanel
 */
public class PingHandler extends CommandHandler<Ping,Pong> {

    public static final String SWITCH_ID = "net.cellar.command.ping.switch";

    private final Switch commandSwitch = new BasicSwitch(SWITCH_ID);

    @Override
    public Pong execute(Ping command) {
        return new Pong(command.getId());
    }

    @Override
    public Class<Ping> getType() {
        return Ping.class;
    }

    @Override
    public Switch getSwitch() {
        return commandSwitch;
    }
}
