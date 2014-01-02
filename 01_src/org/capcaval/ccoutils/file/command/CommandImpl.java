package org.capcaval.ccoutils.file.command;

import org.capcaval.ccoutils.common.CommandResult;

public abstract class CommandImpl<T> implements Command<T>{
	CommandResult result = new CommandResult();

	@Override
	public CommandResult getCommandResult() {
		return this.result;
	}
}
