package org.capcaval.ccoutils.commandline._test.c3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.capcaval.ccoutils.commandline.Command;

public class ProjectFileCreationCommandCommand {
	
	@Command
	public String newProject(String name, String path){
		Path projectPath = Paths.get(path, name);
		Path srcPath = Paths.get(projectPath.toString(), "01_src");
		Path libPath = Paths.get(projectPath.toString(), "02_lib");
		
		
		String returnedMessage = "[C3] INFO : " + projectPath + " is created";
		try {
			Files.createDirectory(projectPath);
			Files.createDirectory(srcPath);
			Files.createDirectory(libPath);
		} catch (IOException e) {
			e.printStackTrace();
			returnedMessage = "[C3] ERROR : can not create the directory : " + projectPath.toString();
		}
		
		return returnedMessage;
	}
}
