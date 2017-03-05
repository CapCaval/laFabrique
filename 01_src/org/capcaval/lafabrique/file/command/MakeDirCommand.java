package org.capcaval.lafabrique.file.command;

import java.io.File;

public class MakeDirCommand extends CommandImpl<MakeDirCommand> implements Command<MakeDirCommand> {
	String dirPath = "";

	@Override
	public MakeDirCommand execute() {
		File dir = new File(dirPath);

		try {
			boolean performed = dir.mkdirs();
			// checkout for errors
			if(performed == false){
				this.result.addErrorMessage("Can not create the directory : " + dirPath);
			}
		} catch (Exception e) {
			this.result.addErrorMessage("Can not create the directory : " + dirPath, e);
		}

		return this;
	}

	public MakeDirCommand name(String dirPath) {
		this.dirPath = dirPath;
		return this;
	}

}
