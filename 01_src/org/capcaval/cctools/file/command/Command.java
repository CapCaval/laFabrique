package org.capcaval.cctools.file.command;

public interface Command<T> {
	public T execute();
	public CommandResult getCommandResult();
}
