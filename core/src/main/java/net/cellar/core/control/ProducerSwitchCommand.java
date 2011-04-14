package net.cellar.core.control;

import net.cellar.core.command.Command;

/**
 * @author iocanel
 */
public class ProducerSwitchCommand extends Command<ProducerSwitchResult> {

    private SwitchStatus status = null;

    /**
     * Constructor
     *
     * @param id
     */
    public ProducerSwitchCommand(String id) {
        super(id);
    }

    /**
     * Constructor
     *
     * @param id
     * @param status
     */
    public ProducerSwitchCommand(String id, SwitchStatus status) {
        super(id);
        this.status = status;
    }

    /**
     * Returns the {@code SwitchStatus}
     *
     * @return
     */
    public SwitchStatus getStatus() {
        return status;
    }

    /**
     * Sets the {@code SwitchStatus}
     *
     * @param status
     */
    public void setStatus(SwitchStatus status) {
        this.status = status;
    }
}
