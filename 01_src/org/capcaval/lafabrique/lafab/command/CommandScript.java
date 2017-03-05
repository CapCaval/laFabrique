package org.capcaval.lafabrique.lafab.command;

import java.nio.file.Path;
import java.util.Map;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.TokenTool;
import org.capcaval.lafabrique.lafab.ScriptDesc;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.project.Project;

public class CommandScript implements LaFabTask{

	@Override
	public CommandResult run(Project proj) {
		return createAllScript(proj);
	}

	public static CommandResult createAllScript(Project proj) {
		CommandResult cr = new CommandResult(Type.INFO, "Start creating all scripts");
		
		for(ScriptDesc desc : proj.script.list){
			CommandResult scriptCr = createScript(desc, proj);
			cr.concat(scriptCr);
		}
		
		return cr;
	}

	private static CommandResult createScript(ScriptDesc desc, Project proj) {
		CommandResult cr = new CommandResult();
		
		Class<?> execType = null;
		try {
			execType = Class.forName(desc.getClassToExecute());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return cr.addErrorMessage("Can not found " + desc.getClassToExecute() + ".class inside the " + proj.binDirPath + " directory.", e);
		}
		
		if(ClassTools.doesClassContainMain(execType) == false){
			cr.addWarningMessage("The class " + execType + " does not contain a main method. Has it can not be executed, the script " + desc .getName() + " is not created.");
			// bye bye 
			return cr;
		}
		
		// -----------------------------------------------------------------------------
		// create the script lafab for windows and linux -------------------------------
		String batStr = FileTools.getLocalTextFile("template.laFab.bat");

		StringBuilder libStr = new StringBuilder(proj.binDirPath.toString()+";");
		String libPathStr = proj.libDirList.get(0).toString();

		// add all libs
		for(String lib : proj.libList){
			libStr.append(libPathStr + "/" + lib);
			libStr.append(";");
		}
		
		// add the produced jar if any
		if(proj.jar.name != null){
			libStr.append(libPathStr + "/" + proj.jar.name);
			libStr.append(";");
		}
		
		String classPathStr = "-cp " + libStr.toString();
		
		// remove the last semicolon character if any
		classPathStr = StringTools.removeEnd(";", classPathStr);
		
		Map<String, String> map = ArrayTools.newMap(
				"appClass", execType.getCanonicalName(),  
				"librairieList", classPathStr);

		batStr = TokenTool.replaceTokenFromReader(batStr, map, '{', '}');

		FileTools.writeFile("./" + desc.getName() + ".bat", batStr);
		
		// linux
		String shStr = FileTools.getLocalTextFile("template.laFab.sh");
		classPathStr = classPathStr.replace(";", ":");
		
		map = ArrayTools.newMap(
				"appClass", execType.getCanonicalName(), 
				"librairieList", classPathStr);

		shStr = TokenTool.replaceTokenFromReader(shStr, map, '{', '}');

		Path p = FileTools.writeFile("./" + desc.getName(), shStr);
		p.toFile().setExecutable(true);
		
		cr.addInfoMessage("New Windows script has been created : " + desc.getName() +".bat");
		cr.addInfoMessage("New Linux script has been created : ./" + desc.getName());

		
		return cr;
	}

}
