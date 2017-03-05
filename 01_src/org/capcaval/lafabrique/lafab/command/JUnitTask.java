package org.capcaval.lafabrique.lafab.command;

import java.io.File;
import java.nio.file.Path;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.project.Project;
import org.capcaval.lafabrique.task.Task;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JUnitTask implements Task<Project>{

	@Override
	public CommandResult run(Project proj) {
		
		CommandResult cr = new CommandResult();
		cr.addMessage("Junit Task");
		
		FileSeekerResult r = FileTools.seekFiles("*.java", proj.sourceDirList[0]);

		File[] fileArray = r.getFileList();

		for (File f : fileArray) {
			// String fileName = f.getPath();

			Class<?> type = this.getClassFromFile(f.toPath(), proj.sourceDirList[0]);

			if (ClassTools.doesClassContainJUnitTest(type)) {
				System.out.println(" --> Test Start on" + type);
				
				Result result = JUnitCore.runClasses(type);
				for (Failure failure : result.getFailures()) {
					System.out.println(failure.toString());
				}
				System.out.println(result.wasSuccessful() + " on " + result.getRunCount() + " tests.");

			}

			// String name =
			// ClassTools.getClassNameFromFullName(fileName.substring(0,
			// fileName.length()-5));
			// Class<?> type = null;
			// try {
			// type = Class.forName(name);
			// } catch (ClassNotFoundException e) {
			// e.printStackTrace();
			// }

		}			

		System.out.println("End of Test @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		
		return cr;
	}

	private Class<?> getClassFromFile(Path filePath, Path srcPath) {
		// first get the class path
		Path classPath = srcPath.relativize(filePath);
		// get the class
		Class<?> type = null;

		String className = this.getFullClassNameFromPathName(classPath.toString());
		
		try {
			type = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return type;
	}

	private String getFullClassNameFromPathName(String pathName) {
		// remove .java if any
		if(pathName.endsWith(".java")){
			pathName = pathName.substring(0, pathName.length()-5);
		}
		
		// replace the slash
		pathName = pathName.replaceAll("/", ".");
		
		return pathName;
	}
}
