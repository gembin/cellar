package net.cellar.utils.ping;

import net.cellar.core.command.CommandHandler;
import net.cellar.core.command.ResultHandler;
import net.cellar.core.control.ManageHandlersResult;
import net.cellar.core.control.Switch;

/**
 * @author iocanel
 */
public class PongHandler extends ResultHandler<Pong> {

 	@Override
	public Class<Pong> getType() {
		return Pong.class;
	}
}
