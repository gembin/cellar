package net.cellar.core.control;


import net.cellar.core.command.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iocanel
 */
public class ManageHandlersResult extends Result {

    public Map<String,String> handlers = new HashMap<String,String>();

    /**
     * Constructor.
     * @param id
     */
    public ManageHandlersResult(String id) {
        super(id);
    }

    public Map<String, String> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, String> handlers) {
        this.handlers = handlers;
    }
}
