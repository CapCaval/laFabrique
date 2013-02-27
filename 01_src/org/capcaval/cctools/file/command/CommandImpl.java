package org.capcaval.cctools.file.command;

public abstract class CommandImpl<T> implements Command<T>{
	CommandResult result = new CommandResult();

	@Override
	public CommandResult getCommandResult() {
		return this.result;
	}
}
