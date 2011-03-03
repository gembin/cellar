package net.cellar.core.control;

import net.cellar.core.command.Command;

/**
 * @author
 */
public class ManageHandlersCommand extends Command<ManageHandlersResult> {

    private String handlesName;
    private Boolean status = Boolean.TRUE;

    /**
     * Constructor
     * @param id
     */
    public ManageHandlersCommand(String id) {
        super(id);
    }

    public String getHandlesName() {
        return handlesName;
    }

    public void setHandlesName(String handlesName) {
        this.handlesName = handlesName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
