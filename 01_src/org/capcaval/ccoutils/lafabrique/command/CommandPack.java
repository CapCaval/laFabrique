package org.capcaval.ccoutils.lafabrique.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.lafabrique.AbstractProject;

public class CommandPack {

	public static void pack(AbstractProject proj){
		// create the directories
		try {
			Files.createDirectory(Paths.get(proj.productionDirPath + "/" + proj.name + proj.version));
			Files.createDirectory(proj.productionDirPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//FileTool.
		
		// laFab + laFab.bat
		// 00_prj
		// 02_lib + ccOutils.jar
		// 30_sample
		
		
	}
	
}
