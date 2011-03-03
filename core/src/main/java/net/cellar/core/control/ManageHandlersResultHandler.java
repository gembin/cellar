package net.cellar.core.control;

import net.cellar.core.command.ResultHandler;

/**
 * @author iocanel
 */
public class ManageHandlersResultHandler extends ResultHandler<ManageHandlersResult> {

	@Override
	public Class<ManageHandlersResult> getType() {
		return ManageHandlersResult.class;
	}
}