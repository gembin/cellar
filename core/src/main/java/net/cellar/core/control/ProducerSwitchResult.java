package net.cellar.core.control;

import net.cellar.core.command.Result;

/**
 * @author iocanel
 */
public class ProducerSwitchResult extends Result {

    protected Boolean sucess = Boolean.TRUE;
    protected Boolean status = Boolean.TRUE;

    /**
     * Constructor
     *
     * @param id
     */
    public ProducerSwitchResult(String id) {
        super(id);
    }

    /**
     * Constructor
     *
     * @param id
     * @param sucess
     */
    public ProducerSwitchResult(String id, Boolean sucess) {
        super(id);
        this.sucess = sucess;
    }

    /**
     * Constructor
     *
     * @param id
     * @param sucess
     * @param status
     */
    public ProducerSwitchResult(String id, Boolean sucess, Boolean status) {
        super(id);
        this.sucess = sucess;
        this.status = status;
    }

    public Boolean getSucess() {
        return sucess;
    }

    public void setSucess(Boolean sucess) {
        this.sucess = sucess;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
