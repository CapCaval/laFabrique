package org.capcaval.ccoutils.file.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CopyCommand extends FromToCommandImpl<CopyCommand>{
	List<String> from = new ArrayList<String>();
	List<String> to = new ArrayList<String>();
	
	@Override
	public ExtendedCopyCommand from(String path) {
		this.from.addAll(FileCommand.compute(path));
		this.from.add(path);
		return new ExtendedCopyCommand(this);
	}
	@Override
	public CopyCommand to(String path) {
		this.to.add(path);
		return this;
	}
	
	@Override
	public CopyCommand execute() {
		Path pathFrom = Paths.get(this.from.get(0));
		Path pathTo = Paths.get(this.to.get(0));
		try {
			Files.copy(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			this.result.addMessage("Copy Failed");
		}		
		return this;		
	}
}
