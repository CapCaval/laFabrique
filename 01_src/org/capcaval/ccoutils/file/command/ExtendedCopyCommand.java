package org.capcaval.ccoutils.file.command;

public class ExtendedCopyCommand extends CopyCommand{
	public ExtendedCopyCommand(CopyCommand copyCommand) {
		this.from = copyCommand.from;
		this.to = copyCommand.to;
	}

	ExtendedCopyCommand exclude(String path){
		return null;
	}
}
