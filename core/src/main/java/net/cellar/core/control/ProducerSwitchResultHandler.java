package net.cellar.core.control;

import net.cellar.core.command.ResultHandler;

/**
 * @author iocanel
 */
public class ProducerSwitchResultHandler extends ResultHandler<ProducerSwitchResult> {

	@Override
	public Class<ProducerSwitchResult> getType() {
		return  ProducerSwitchResult.class;
	}
}
