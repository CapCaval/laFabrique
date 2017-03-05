package org.capcaval.lafabrique.lafab.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.TokenTool;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.project.Project;

public class CommandEclipseProject {
	
	public static CommandResult updateEclipseProject(Project proj){
		
		List<Map<String, String>> list = new ArrayList<>();
		// add the lib list of program source
		for(String path : proj.libList){
			list.add(ArrayTools.newMap("jarName", path));
		}
		// add also the lib list of program source
		for(String path : proj.test.libArray){
			list.add(ArrayTools.newMap("jarName", path));
		}

		
		String templateStr = FileTools.getLocalTextFile("template.classpath");

		// Inside libraries 
		String classpath = TokenTool.replaceBlocks(
				templateStr,
				list,
				"#start#", 
				"#end#", 
				'{', 
				'}');
		
		Map<String, String> map = new HashMap<>();
		map.put("jdkVersion", proj.jdkVersion.name());
		map.put("output", proj.binIdeDirPath.toString());
		for(Path p : proj.sourceDirList){
			map.put("srcPath", p.toString());
		}
		
		// set the jdk and the output
		classpath =  TokenTool.replaceTokenFromReader(
				classpath, 
				map, 
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
		Writer writer = TokenTool.replaceTokenFromReader(
				pStream, 
				ArrayTools.newMap("projectName", proj.name), 
				'{',
				'}');		
		
		FileTools.writeFile( "./.classpath", classpath);
		FileTools.writeFile( "./.project", writer.toString());
		
		return new CommandResult();
	}

}
