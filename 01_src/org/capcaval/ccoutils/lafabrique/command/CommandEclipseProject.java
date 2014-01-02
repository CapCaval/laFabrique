package org.capcaval.ccoutils.lafabrique.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.file.TokenTool;
import org.capcaval.ccoutils.lafabrique.AbstractProject;
import org.capcaval.ccoutils.lang.ArrayTools;

public class CommandEclipseProject {
	
	public static CommandResult updateEclipseProject(AbstractProject proj){
		
		List<Map<String, String>> list = new ArrayList<>();
		for(String path : proj.libList){
			list.add(ArrayTools.newMap("jarName", path));
		}

		// Inside libraries 
		String classpath =  TokenTool.replaceBlocksInsideFile(
				"template.classpath",
				list,
				"#start#", 
				"#end#", 
				'{', 
				'}');
		
		// set the jdk and the output
		classpath =  TokenTool.replaceTokenFromReader(
				classpath, 
				ArrayTools.newMap("jdkVersion", proj.jdkVersion, "output", proj.binDirPath.toString()), 
				'{', 
				'}');
		
		InputStream projectStream = null;
		// project
		try {
			projectStream = CommandEclipseProject.class.getResourceAsStream("template.project");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		 BufferedReader pStream = new BufferedReader(new InputStreamReader(projectStream));
		 try {
			Writer writer = TokenTool.replaceTokenFromReader(
					pStream, 
					ArrayTools.newMap("projectName", proj.name), 
					'{',
					'}');		
		
			FileTools.writeFile( "./.classpath", classpath);
			FileTools.writeFile( "./.project", writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new CommandResult("Eclipse project updated");
	}

}
