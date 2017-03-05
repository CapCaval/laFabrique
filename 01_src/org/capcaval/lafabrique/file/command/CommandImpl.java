package org.capcaval.lafabrique.file.command;

import org.capcaval.lafabrique.common.CommandResult;

public abstract class CommandImpl<T> implements Command<T>{
	CommandResult result = new CommandResult();

	@Override
	public CommandResult getCommandResult() {
		return this.result;
	}
}
