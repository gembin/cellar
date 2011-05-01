package net.cellar.core.control;

import net.cellar.core.command.ResultHandler;

/**
 * @author: iocanel
 */
public class ManageGroupResultHandler extends ResultHandler<ManageGroupResult> {

    @Override
    public Class<ManageGroupResult> getType() {
        return ManageGroupResult.class;
    }
}
