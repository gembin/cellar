package net.cellar.core.command;

import net.cellar.core.event.Event;

/**
 * @author iocanel
 */
public class Result extends Event {

    public Result(String id) {
        super(id);
        this.force = true;
    }

    @Override
    public Boolean getForce() {
        return true;
    }
}
