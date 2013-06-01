package org.capcaval.ccoutils.lafabrique.command;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.file.TokenTool;
import org.capcaval.ccoutils.file.command.CommandResult;
import org.capcaval.ccoutils.lang.ArrayTools;
import org.capcaval.ccproject.AbstractProject;

public class CommandEclipseProject {
	
	public static CommandResult updateEclipseProject(AbstractProject proj){
		
		URL templateUrl =  CommandEclipseProject.class.getResource("template.classpath");
		
		List<Map<String, String>> list = new ArrayList();
		for(String path : proj.libList){
			list.add(ArrayTools.newMap("jarName", path));
		}
		String pathStr = templateUrl.getFile().toString();
		String templateStr = "";
		try {
			templateStr = FileTool.readStringfromFile(Paths.get(pathStr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Inside libraries 
		String classpath =  TokenTool.replaceBlocks(
				templateStr,
				list,
				"#start#", 
				"#end#", 
				'{', 
				'}');
		
		// set the jdk and the output
		classpath =  TokenTool.replaceTokenFromReader(
				classpath, 
				ArrayTools.newMap("jdkVersion", proj.jdkVersion, "output", proj.productionDirPath.toString()), 
				'{', 
				'}');
		
		// project
		URL templateProjectUrl =  CommandEclipseProject.class.getResource("template.project");
		String projectStr = templateProjectUrl.getFile().toString();

		try {
			projectStr = FileTool.readStringfromFile(Paths.get(projectStr));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		projectStr =  TokenTool.replaceTokenFromReader(
				projectStr, 
				ArrayTools.newMap("projectName", proj.name), 
				'{', 
				'}');
		
		
		try {
			FileTool.writeFile("test.classpath", classpath);
			FileTool.writeFile("test.project", projectStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new CommandResult();
	}

}
