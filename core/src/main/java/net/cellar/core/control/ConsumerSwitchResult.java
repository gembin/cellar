package net.cellar.core.control;

import net.cellar.core.command.Result;

/**
 * @author iocanel
 */
public class ConsumerSwitchResult extends Result {

    protected Boolean sucess = Boolean.TRUE;
    protected Boolean status = Boolean.TRUE;

    /**
     * Constructor
     *
     * @param id
     */
    public ConsumerSwitchResult(String id) {
        super(id);
    }

    /**
     * Constructor
     *
     * @param id
     * @param sucess
     */
    public ConsumerSwitchResult(String id, Boolean sucess) {
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
    public ConsumerSwitchResult(String id, Boolean sucess, Boolean status) {
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
