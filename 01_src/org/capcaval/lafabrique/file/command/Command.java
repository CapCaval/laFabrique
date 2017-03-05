package org.capcaval.lafabrique.file.command;

import org.capcaval.lafabrique.common.CommandResult;

public interface Command<T> {
	public T execute();
	public CommandResult getCommandResult();
}
