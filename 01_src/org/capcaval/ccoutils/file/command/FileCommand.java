package org.capcaval.ccoutils.file.command;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileCommand {

	static public List<String> compute(String path){
		// check that this is * special character
		boolean severalFile = path.contains("*");
		if(severalFile == true){
			String[] resultArray = path.split("*");
		}

		Files.isDirectory(Paths.get(path));
		
		// get all the file
		
		
		return null;
	}

}
