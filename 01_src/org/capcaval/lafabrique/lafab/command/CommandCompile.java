package org.capcaval.lafabrique.lafab.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.compiler.CompilerTools;
import org.capcaval.lafabrique.compiler.JavaBuilder;
import org.capcaval.lafabrique.compiler._impl.CompileResult;
import org.capcaval.lafabrique.compiler._impl.CompileResult.CompilationResultEnum;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.command.FileCmd;
import org.capcaval.lafabrique.file.pathfilter.PathFilter;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.project.Project;


public class CommandCompile implements LaFabTask{
	
	public static CommandResult compile(Project proj){
		CommandResult cr = new CommandResult(Type.INFO, "start compile project " + proj.name);
		
		JavaBuilder compiler = CompilerTools.getJavaBuilder(proj);
		
		cr.addInfoMessage("JDK version is " + compiler.getJVM().getName());

		if(compiler == null){
			cr.addErrorMessage("CANNOT find JDK. Please install a 1.7 version or above.");
			return cr;
		}
		// clean all needed packages
		for(String packageStr : proj.packageNameList){
			cleanProductionDirectory(proj.binDirPath.resolve(packageStr));
			cr.addInfoMessage("clean " + proj.binDirPath.resolve(packageStr));
		}
		FileSeekerResult result = null;
		
		try {
			Path[] sourcePathArray = computeAllSourcePath(proj.sourceDirList, proj.packageNameList, proj.name, proj.projectDir, cr);
			// get all the source file from the given directories
			result = FileTools.seekFiles("*.java", sourcePathArray);
			
			cr.addMessage(Type.INFO, "Found " + result.getFileList().length + " classes inside " + Arrays.toString(sourcePathArray));
	
	        Path[] dirArray = proj.libDirList.toArray(new Path[0]);
	        
	        Path[] libPathArray = FileTools.getFileSFromNamesAndRootDirs(dirArray, proj.libList);
	        Path[] libTestPathArray = FileTools.getFileSFromNamesAndRootDirs(dirArray, proj.test.libArray);
	        Path[] binPath = new Path[]{compiler.getOutputBinDir()};
	        
	        // in order to compile all concat the test Jar with the application one.
	        libPathArray = ArrayTools.concat(libPathArray, libTestPathArray);
	        libPathArray = ArrayTools.concat(libPathArray, binPath);
	        
	        FileCmd.makeDir.name(proj.binDirPath.toString()).execute();
	        
	        CompileResult compileResult = compiler.compile(result.getPathList(), libPathArray);
	        cr.addMessage(compileResult.getCompilationLog());
	        
	        if(compileResult.getCompilationResultEnum()!=CompilationResultEnum.error){
				// copy all the non java file
				cr.concat( copyAllNonJavaSource(proj));
				cr.addMessage(Type.INFO, "Compilation is successful. Classes are in " + proj.binDirPath);
	        }else{
				cr.addErrorMessage("Compilation failed");
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cr;
    }
	
	public static CommandResult compile2(Project proj){
		CommandResult cr = new CommandResult(Type.INFO, "start compile project " + proj.name);
		
		JavaCompiler compiler = CompilerTools.getJavaCompiler(proj);
		
		cr.addInfoMessage("JDK version is " + Arrays.toString(compiler.getSourceVersions().toArray()));

		if(compiler ==null){
			cr.addErrorMessage("CANNOT find JDK. Please install a 1.7 version or above.");
			return cr;
		}
		// clean all needed packages
		for(String packageStr : proj.packageNameList){
			cleanProductionDirectory(proj.binDirPath.resolve(packageStr));
		}
		FileSeekerResult result = null;
		
		JavaCompiler.CompilationTask task = null;
		
		try {
			Path[] sourcePathArray = computeAllSourcePath(proj.sourceDirList, proj.packageNameList, proj.name, proj.projectDir, cr);
			// get all the source file from the given directories
			result = FileTools.seekFiles("*.java", sourcePathArray);
			
			cr.addMessage(Type.INFO, "Found " + result.getFileList().length + " classes inside " + Arrays.toString(sourcePathArray));
	
	        String classpath=System.getProperty("java.class.path");
	        Path[] dirArray = proj.libDirList.toArray(new Path[0]);
	        
	        Path[] libPathArray = FileTools.getFileSFromNamesAndRootDirs(dirArray, proj.libList);
	        Path[] libTestPathArray = FileTools.getFileSFromNamesAndRootDirs(dirArray, proj.test.libArray);
	        // in order to compile all concat the test Jar with the application one.
	        libPathArray = ArrayTools.concat(libPathArray, libTestPathArray);
	        
	        String fullpath= classpath + File.pathSeparator + "."+  File.pathSeparator + ArrayTools.toStringWithDelimiter(File.pathSeparatorChar, libPathArray);
	        
	        FileCmd.makeDir.name(proj.binDirPath.toString()).execute();
	        
	        List<String> optionList =  ArrayTools.newArrayList(
	        		"-g",
	        		"-classpath",fullpath,
	        		"-d", proj.binDirPath.toString());
	        
	        StandardJavaFileManager sfm = compiler.getStandardFileManager(null, null, null);
	        Iterable<? extends JavaFileObject> fileObjects = sfm.getJavaFileObjects(result.getStringFileList());
        
	        task = compiler.getTask(
	        		null, null, null,
	        		optionList,null,fileObjects);

			sfm.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

		boolean isSuccessFull = false;
		try{
			isSuccessFull = task.call();
		}catch(Exception e){
			isSuccessFull = false;
			// keep going
		}
			
		if (isSuccessFull == true) {
			// copy all the non java file
			cr.concat( copyAllNonJavaSource(proj));
			
			cr.addMessage(Type.INFO, "Compilation is successful. Classes are in " + proj.binDirPath);
		}else{
			cr.addErrorMessage("Compilation failed");
		}
		
		return cr;
    }

	private static void cleanProductionDirectory(Path productionDirPath) {
		try{
			// delete the production if existing
			FileTools.deleteFile( productionDirPath);
			// recreate it
			Files.createDirectories( productionDirPath);}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static CommandResult copyAllNonJavaSource(Project proj) {
		CommandResult cr = new CommandResult();
		PathFilter fileFilter = newNonJavaFilter();
		
		for(Path path : proj.sourceDirList){
			for(String packageName : proj.packageNameList){
				String packagePath = packageName.replace(".","/");
				Path p = Paths.get(packagePath);
				// copy the source file
				FileTools.copy( p, proj.binDirPath, path, fileFilter);
			}
		}
		return cr;
	}
	
	private static PathFilter newNonJavaFilter(){
		PathFilter filter = new PathFilter() {
			@Override
			public boolean isPathValid(Path path) {
				// do not copy java file
				return (path.toString().endsWith(".java")==false);
				}
			};
		return filter;
	}

	private static Path[] computeAllSourcePath(Path[] sourceList, String[] packageNameList, String name, Path projectDir, CommandResult cr) {
		List<Path> list = new ArrayList<>();
		for(Path path : sourceList){
			for(String packageName : packageNameList){
				// build file name for classes
				String packagePath = packageName.replace(".","/");
				Path p = Paths.get(path.toString()+"/" + packagePath);
				
				// add it for futur compilation
				list.add(p);
			}
		}
		
//		// add the project dir
//		FileSeekerResult r = null;
//		try {
//			r = FileTools.seekFiles(name + ".java", projectDir);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		list.add(r.getPathList()[0]);
		
		return list.toArray(new Path[0]);
	}
	
	public void createProjectInfo(){
		
	}

	@Override
	public CommandResult run(Project proj) {
		return compile(proj);
	}
}
