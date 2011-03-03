package net.cellar.core.control;

import net.cellar.core.command.ResultHandler;

/**
 * @author iocanel
 */
public class ConsumerSwitchResultHandler extends ResultHandler<ConsumerSwitchResult> {

	@Override
	public Class<ConsumerSwitchResult> getType() {
		return ConsumerSwitchResult.class;
	}
}
