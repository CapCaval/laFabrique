package org.capcaval.ccoutils.file.command;

import org.capcaval.ccoutils.common.CommandResult;

public interface Command<T> {
	public T execute();
	public CommandResult getCommandResult();
}
