package org.capcaval.cctools.file.command;

import java.io.File;

public class MakeDirCommand extends CommandImpl<MakeDirCommand>implements Command<MakeDirCommand>{
	String dirPath;
	
	@Override
	public MakeDirCommand execute() {
		File dir = new File(dirPath);  
		dir.mkdir();
		return this;
	}

	public MakeDirCommand name(String dirPath) {
		this.dirPath = dirPath;
		return this;
	}

}
