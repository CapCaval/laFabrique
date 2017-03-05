package org.capcaval.lafabrique.lafab.command;

import java.nio.file.Path;
import java.util.Map;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.TokenTool;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.lang.SystemTools.OSType;
import org.capcaval.lafabrique.project.Project;

public class CommandNewApplication {

	public static CommandResult newApplication(Project proj, String fullClassName) {
		CommandResult cr = new CommandResult();

		String className = ClassTools.getClassNameFromFullName(fullClassName);
		String packageName = ClassTools.getPackageNameFromFullName(fullClassName);

		// -----------------------------------------------------
		// create the project file -----------------------------
		String templateStr = FileTools.getLocalTextFile("template.application");

		// add the package line if needed
		if (packageName.equals("") == false) {
			packageName = "package " + packageName + ";";
		}

		Map<String, String> map = ArrayTools.newMap("appName", className, "authorName",
				proj.authorList[0] != null ? proj.authorList[0] : "", "packageName", packageName, "className",
				className);

		String appClass = TokenTool.replaceTokenFromReader(templateStr, map, '{', '}');

		// String[] multiLineStr = appClass.split("\n");

		FileTools
				.writeFile(proj.sourceDirList[0].resolve(ClassTools.getFileNameFromClassName(fullClassName)), appClass);

		// -----------------------------------------------------------------------------
		// create the script lafab for windows and linux
		// -------------------------------
		String batStr = FileTools.getLocalTextFile("template.laFab.bat");

		StringBuilder libStr = new StringBuilder(proj.binDirPath.toString() + ";");
		String libPathStr = proj.libDirList.get(0).toString();
		for (String lib : proj.libList) {
			libStr.append(libPathStr + "/" + lib);
			libStr.append(";");
		}

		String classPathStr = libStr.toString();
		if (proj.jar.name != "") {
			classPathStr = classPathStr + proj.productionDirPath.toString() + "/" + proj.jar.name;
		}
		if (proj.libList.length > 0) {
			classPathStr = "-cp " + classPathStr;
		}

		if (classPathStr.charAt(classPathStr.length() - 1) == ';') {
			classPathStr = classPathStr.substring(0, classPathStr.length() - 1);
		}

		map = ArrayTools.newMap("appClass", fullClassName, "librairieList", classPathStr);

		batStr = TokenTool.replaceTokenFromReader(batStr, map, '{', '}');

		FileTools.writeFile("./" + className + ".bat", batStr);

		// linux
		String shStr = FileTools.getLocalTextFile("template.laFab.sh");
		classPathStr = classPathStr.replace(";", ":");

		map = ArrayTools.newMap("appClass", fullClassName, "librairieList", classPathStr);

		shStr = TokenTool.replaceTokenFromReader(shStr, map, '{', '}');

		Path p = FileTools.writeFile("./" + className, shStr);
		p.toFile().setExecutable(true);

		if (SystemTools.getOSType() == OSType.Windows) {
			cr.addInfoMessage("New application has been created. To run it, use the script : " + className + ".bat");
		} else {
			cr.addInfoMessage("New application has been created. To run it, use the script : ./" + className);
		}

		return cr;
	}

}
