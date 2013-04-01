package org.capcaval.ccoutils.file.command;

public interface Command<T> {
	public T execute();
	public CommandResult getCommandResult();
}
