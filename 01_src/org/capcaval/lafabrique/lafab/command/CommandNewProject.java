package org.capcaval.lafabrique.lafab.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.FileTools.FilePropertyEnum;
import org.capcaval.lafabrique.lafab.CurrentConfig;
import org.capcaval.lafabrique.lang.ArrayTools;

public class CommandNewProject {

	public static CommandResult newProject(String name, Path projectPath, CurrentConfig defaultConfigProject) {
		CommandResult cr = new CommandResult();
		
		List<Path> subDirList = ArrayTools.newArrayList(
				defaultConfigProject.projectDir, 
				defaultConfigProject.projectDir.resolve("tools"));
		// add all the source directory
		for(Path srcPath : defaultConfigProject.sourceDirList){
			subDirList.add(srcPath);
		}
		// add all the lib directory
		for(Path libPath : defaultConfigProject.libDirList){
			subDirList.add(libPath);
		}
		
		cr.addInfoMessage("create all following sub-dirs at " + projectPath.toString());

		try {
			for (Path dir : subDirList) {
				Path p = Paths.get(projectPath.toString(), dir.toString());

				if (p.toFile().exists() == true) {
					System.out.print("Directory " + p + " already exist, do you want to overwrite it ? ");
					String str = System.console().readLine();
					if (str.contains("y")) {
						Files.createDirectories(p);
					}
				} else {
					Files.createDirectories(p);
					cr.addInfoMessage( p + " is created");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			cr.addErrorMessage("Can not create the directory : " + projectPath.toString());
		}

//		try {
//			cr.addMessage(CommandNewProject.class.getResource("laFab").toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// copy the laFabrique lib
		// to do verify that the jar is existing otherwise get it inside 20_prod
		if (Files.exists(Paths.get("02_lib/laFabrique.jar"))) {
			copyFile( "02_lib/laFabrique.jar", projectPath.toString() + "/02_lib/laFabrique.jar");
		} else if (Files.exists(Paths.get("20_prod/ccOutils.jar"))) {
			copyFile("20_prod/laFabrique.jar", projectPath.toString() + "/02_lib/laFabrique.jar");
		} else {
			cr.addErrorMessage("Can not find laFabrique.jar inside 02_lib or inside 20_prod directories.");
		}

		InputStream laFabStream = CommandNewProject.class.getResourceAsStream("laFab");
		FileTools.saveInputStream(laFabStream, Paths.get(projectPath.toString(), "laFab"), FilePropertyEnum.xr_);

		InputStream laFabBatStream = CommandNewProject.class.getResourceAsStream("laFab.bat");
		FileTools.saveInputStream(laFabBatStream, Paths.get(projectPath.toString(), "laFab.bat"), FilePropertyEnum.xr_);

		// create a new Proj
		try {
			// Path templatePath =
			InputStream prjStream = CommandNewProject.class.getResourceAsStream("Project.java.template");

			InputStreamReader prjReader = new InputStreamReader(prjStream);

			FileTools.replaceTokenInFile(prjReader, Paths.get(projectPath.toString() + "/00_prj/prj/", name + ".java"),
					ArrayTools.newMap("projectName", name), '{', '}');

		
			// create the default project
			InputStream defPrjStream = CommandNewProject.class.getResourceAsStream("template.defaultProject");

			InputStreamReader defPrjReader = new InputStreamReader(defPrjStream);

			FileTools.replaceTokenInFile(defPrjReader, Paths.get(projectPath.toString() + "/00_prj/prj/DefaultProject.java"),
					ArrayTools.newMap("defaultProjectClass", name), '{', '}');
			
			cr.addBlankLine();
			cr.addInfoMessage("Project " + name + " has been created.");
			
			// copy the tools
			String[] toolsArray = new String[]{
					"00_prj/tools/Build.java",
					"00_prj/tools/UpdateEclipseProject.java"
			};
			for(String toolStr : toolsArray){
				if (Files.exists(Paths.get(toolStr))) {
					copyFile( toolStr, projectPath.toString() + "/"+toolStr);
					cr.addInfoMessage("add " + toolStr);
				}
				else{
					cr.addErrorMessage( toolStr + "does not exist.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cr;
	}
	
	public static CommandResult copyFile(String srcFile, String destDir) {
		CommandResult cr = new CommandResult();
		try {
			// copy the file
			Files.copy(Paths.get(srcFile), Paths.get(destDir), StandardCopyOption.REPLACE_EXISTING);
			cr.addInfoMessage( Paths.get(destDir + "/" + srcFile).toFile().toString()
					+ " is added");

		} catch (IOException e) {
			cr.addErrorMessage("Can not copy the file : " + Paths.get(srcFile) + " to "
					+ Paths.get(destDir + "/" + srcFile));
			cr.addMessage(e.toString());
			if (e.getCause() != null) {
				cr.addMessage(e.getCause().toString());
			}
		}
		return cr;
		
	}

}
