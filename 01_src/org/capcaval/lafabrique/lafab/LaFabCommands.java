package org.capcaval.lafabrique.lafab;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.classgen.ClassGenTools;
import org.capcaval.lafabrique.classgen.EnumNameValue;
import org.capcaval.lafabrique.commandline.Command;
import org.capcaval.lafabrique.commandline.CommandParam;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.compiler.CompilerTools;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lafab.command.CommandEclipseProject;
import org.capcaval.lafabrique.lafab.command.CommandNewApplication;
import org.capcaval.lafabrique.lafab.command.CommandNewProject;
import org.capcaval.lafabrique.lafab.command.CommandPack;
import org.capcaval.lafabrique.lafab.command.JUnitTask;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.JVM;
import org.capcaval.lafabrique.lang.JVMTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.project.Project;
import org.capcaval.lafabrique.project.ProjectInfo;
import org.capcaval.lafabrique.task.TaskTools;

public class LaFabCommands {

	
	@Command(desc = "Update eclipse project from the default laFabrique project.")
	public String updateEclipseProject(){
		CommandResult cr = new CommandResult();

		Config config = CurrentConfig.getConfig();

		if (FileTools.isFileExist(config.projectDir) == false) {
			cr.addErrorMessage("There is no workspace inside " + config.projectDir
					+ " directory. Please create one in order to compile something.");
			return cr.toString();
		}
		Project prj = this.getDefaultProject();

		cr.addInfoMessage("Default project is : " + prj.name);
		cr.addMessage(this.updateEclipseProject(prj.name));

		return cr.toString();
	}
	
	@Command(desc = "Update eclipse project from the given laFabrique project.")
	public String updateEclipseProject(
			@CommandParam(name = "project name", desc = "Define the name of the project to be updated.") String projectNameStr) {
		CommandResult cr1 = new CommandResult();
		cr1.addInfoMessage("Update Eclipse project named " + projectNameStr);

		// compile the project first
		Project project = this.getProject(projectNameStr);

		if (project == null) {
			StringMultiLine returnedMessage = new StringMultiLine("Error project " + projectNameStr
					+ " can not be found.", "                     the file 00_prj/prj/" + projectNameStr
					+ ".java can not be found.");

			CommandResult crError = new CommandResult(returnedMessage.toString());

			return cr1.toString() + crError.toString();
		}

		CommandResult cr2 = CommandEclipseProject.updateEclipseProject(project);

		if (cr2.getType() != Type.ERROR) {
			cr2.addInfoMessage("Eclipse project updated successfully.");
		}

		// by default do it one the current directory
		return cr1.concat(cr2).toString();
	}

	@Command(desc = "Create an application inside this project.")
	public String newApp(
			@CommandParam(name = "application full class name", desc = "Define the name of the application to be created.") String fullClassName) {
		CommandResult cr1 = new CommandResult();
		cr1.addInfoMessage("Create new application " + fullClassName);

		// compile the default project first
		Project project = this.getDefaultProject();

		if (project == null) {
			// there is no Project so make a new one with the name of the app
			System.out.println(newProject(ClassTools.getClassNameFromFullName(fullClassName), ".."));
			// compile the project
			System.out.println("************* build ***");
			System.out.println(build());
			// get the project
			project = this.getDefaultProject();
		}

		CommandResult cr2 = CommandNewApplication.newApplication(project, fullClassName);

		cr1.concat(cr2);

		build();

		// by default do it one the current directory
		return cr1.toString();
	}

	@Command
	public String newProject(
			@CommandParam(name = "project name", desc = "Define the name of the project.") String name,
			@CommandParam(name = "project path", desc = "Define the path of the project.") String pathStr) {
		// get the default configuration of project
		CurrentConfig defaultConfigProject = new CurrentConfig();

		Path projectPath = Paths.get(pathStr, name);

		// clean the directory
		if (projectPath.toFile().exists() == true) {
			FileTools.deleteFile(projectPath);
		}

		CommandResult cr = CommandNewProject.newProject(name, projectPath, defaultConfigProject);

		return cr.toString();
	}

	

	private CommandResult checkAndCorrectProject(Project prj) {
		CommandResult cr = new CommandResult();
		
		// check if the packages exist
		for(String packageStr : prj.packageNameList){
			// convert the class name to a path name
			String fullPackageStr = packageStr.replace(".", "/");
			boolean isFileExist = false;
			for(Path srcDir : prj.sourceDirList){
				if(FileTools.isFileExist(srcDir.resolve(fullPackageStr))){
					// it exists set it as true and exit
					isFileExist = true;
					break;
				}
			}
			if(isFileExist == false){
				// remove it from the project list and warn the users
				prj.packageNameList = ArrayTools.removeElement(packageStr, prj.packageNameList);
				cr.addWarningMessage("Inside the project " + prj.name + ", the following source package does not exist : "+ packageStr);
				cr.addWarningMessage( packageStr + " will not be computed.");
			}
		}
		
		return cr;
	}

	@Command(desc = "build default project")
	public String build() {
		CommandResult cr = new CommandResult();
		
		try{
		
		Config config = CurrentConfig.getConfig();

		if (FileTools.isFileExist(config.projectDir) == false) {
			cr.addErrorMessage("There is no workspace inside " + config.projectDir
					+ " directory. Please create one in order to compile something.");
			return cr.toString();
		}
		// get the default project
		Project prj = this.getDefaultProject();
		cr.addInfoMessage("Build default project.");
		
		// check out project
		cr.addMessage(build(prj.name));

		}catch(Exception e){
			cr.addErrorMessage("Build failed : " + e.toString());
		}
		
		return cr.toString();
	}

	
	@Command(desc = "build project named in paramater")
	public String build(
			@CommandParam(name = "project name", desc = "Name of the project to be built.") 
			String projectStr) {
		CommandResult.applicationName.set("laFabrique");

		CommandResult cr = new CommandResult(Type.INFO, "Start building " + projectStr + " project.");

		Project proj = this.getProject(projectStr);

		if (proj == null) {
			cr.addErrorMessage("Project " + projectStr + " can not be found.");
			cr.addMessage("                     the file 00_prj/prj/" + projectStr + ".java can not be found.");
			// bye bye
			return cr.toString();
		}
		
		ProjectInfo projectInfo = new ProjectInfo(
				proj.name,
				ArrayTools.toStringWithDelimiter(' ', proj.authorList),
				proj.version
				);
		
		// generate all the script's project file info 
		for(ScriptDesc script : proj.script.list){
			String className = "prj."+ ClassTools.getClassNameFromFullName(script.classToExecute)+"ProjectInfo";
			
			ClassGenTools.generateClass(
					proj.sourceDirList[0],
					className, projectInfo);
			
			cr.addInfoMessage("ProjectInfo  generated at : " + proj.sourceDirList[0] + "/" + className.replace(".", "/")+".java");
		}
		
		// generate also a default project which is not link to a script
		ClassGenTools.generateClass(
				proj.sourceDirList[0],
				"prj.Default", projectInfo);
		
		
		// add the prj to be compiled
		proj.packageNameList = ArrayTools.add("prj", proj.packageNameList);
		
		// check out project
		cr.concat(checkAndCorrectProject(proj));
		
		cr.addInfoMessage("Version to be built is : " + proj.version);
		cr.addBlankLine();

		// execute all the configured tasks 
		CommandResult crTasks = TaskTools.runAll(proj, proj);
		cr.concat(crTasks);

		return cr.toString();
	}

	@Command(desc = "pack project")
	public CommandResult pack() {
		CommandResult cr = new CommandResult();

		Project prj = this.getDefaultProject();
		cr.addInfoMessage("Default project is : " + prj.name);

		CommandResult packCr = this.pack(prj.name);

		// concat
		cr.concat(packCr);

		return cr;
	}

	@Command(desc = "pack project")
	public CommandResult pack(
			@CommandParam(name = "project name", desc = "Name of the project to be packed.") String projectStr) {
		CommandResult cr = new CommandResult();

		// compile and get the project first
		Project proj = this.getProject(projectStr);

		if (proj == null) {
			cr.addErrorMessage("Project " + projectStr + " can not be found.");
			cr.addMessage("                     the file 00_prj/prj/" + projectStr + ".java can not be found.");
			// error = true;
			return cr;
		}

		cr.addInfoMessage("Start to pack project " + projectStr + " project.");

		CommandResult result = CommandPack.pack(proj);
		cr.concat(result);

		return cr;
	}

	public Project getProject(String projectName) {
		// first compile all the project to load it
		return this.getProject(projectName, true);
	}

	public Project getProject(String projectName, boolean isCompilationNeeded) {
		String filePath = "prj/" + projectName + ".java";

		// if the project do not exist return null
		if (FileTools.isFileExist("00_prj/" + filePath) == false) {
			return null;
		}

		Config config = CurrentConfig.getConfig();

		// first compile all project
		if (isCompilationNeeded == true) {
			CommandResult r = CompilerTools.compileAll(config.binDirPath.toString(), config.projectDir.toString());

			if (r.getType() == Type.ERROR) {
				throw new RuntimeException("Error can not compile : " + r.toString());
			}
		}

		// secondly get the requested project
		Project prj = null;
		try{
			prj = ClassTools.getFileNewInstance(filePath, config.binDirPath.toString());
		}catch(Exception e){
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			// There is a compilation dependency loop
			// so compile twice
			prj = this.getProject(projectName, isCompilationNeeded);
		}

		return prj;
	}

	public Project getDefaultProject() {
		Config config = CurrentConfig.getConfig();

		// first compile the system dir
		CommandResult cr = CompilerTools.compileAll(config.binDirPath.toString(), config.projectDir.toString()+"/system");
		
		// first compile all project
		cr.concat(CompilerTools.compileAll(config.binDirPath.toString(), config.projectDir.toString()));
		if (cr.getType() == Type.ERROR) {
			throw new RuntimeException("Impossible to compile the project directory at " + config.projectDir.toString()
					+ "\n" + cr.toString());
		}

		// secondly get the default project
		AbstractDefaultProject defProj = null;
		try {
			defProj = ClassTools.getFileNewInstance("prj/DefaultProject.java", config.binDirPath.toString());
		} catch (Throwable t) {
			t.printStackTrace();
		}

		Project p = getProject(defProj.getDefaultProject().getSimpleName(), false);

		return p;
	}

	@Command(desc = "Test project.")
	public CommandResult test() {
		CommandResult cr = new CommandResult();

		Project prj = this.getDefaultProject();
		cr.addInfoMessage("Default project is : " + prj.name);

		CommandResult testCr = this.test(prj.name);

		// concat
		cr.concat(testCr);

		return cr;
	}
	
	@Command(desc = "Test project.")
	public CommandResult test(String projectStr) {
		JUnitTask junitTask = new JUnitTask();
		
		CommandResult cr = new CommandResult();

		// compile and get the project first
		Project proj = this.getProject(projectStr);

		if (proj == null) {
			cr.addErrorMessage("Project " + projectStr + " can not be found.");
			cr.addMessage("                     the file 00_prj/prj/" + projectStr + ".java can not be found.");
			// error = true;
			return cr;
		}

		cr.addInfoMessage("Start to test project " + projectStr + " project.");
		
		
		CommandResult testCr = junitTask.run(proj);
		
		// concat result
		cr.concat(testCr);

		return cr;
	}
	
	@Command(desc = "Seek JVM from the rootDir.")
	public CommandResult seekJVM(String rootDir) {
		CommandResult cr = new CommandResult();
		Path p = Paths.get(rootDir);
		cr.addInfoMessage("Start to look at JVM inside " + p.toAbsolutePath().toString());
		// get all JVM
		JVM[] jvmArray = JVMTools.findJDK(p);
		
		cr.addInfoMessage("found " + jvmArray.length + " JVM.");
		for(JVM jvm : jvmArray){
			cr.addInfoMessage(jvm.toString());
		}
		
		Project prj = getDefaultProject();
		
		List<Object> list = new ArrayList<>();
		
		for(JVM jvm : jvmArray){
			String name = jvm.getName().replace(".", "_");
			list.add(new EnumNameValue(name));
			list.add(jvm.getPath());
		}
		
		// generate the enum describing all available JVM
		ClassGenTools.generateEnum( prj.projectDir, "system.JDKEnum", list.toArray(new Object[0]));
		
		return cr;
	}
}
