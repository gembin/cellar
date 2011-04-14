package net.cellar.core.command;

import net.cellar.core.event.Event;

/**
 * @author iocanel
 */
public class Result extends Event {

    public Result(String id) {
        super(id);
    }

    @Override
    public Boolean getBypassSwitches() {
        return true;
    }
}
