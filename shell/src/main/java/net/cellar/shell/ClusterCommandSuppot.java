package net.cellar.shell;

import net.cellar.core.command.ExecutionContext;
import net.cellar.core.shell.CellarCommandSupport;

/**
 * @author: iocanel
 */
public abstract class ClusterCommandSuppot extends CellarCommandSupport {

    protected ExecutionContext executionContext;

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }
}
