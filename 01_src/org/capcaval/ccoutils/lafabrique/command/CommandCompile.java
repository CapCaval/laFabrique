package org.capcaval.ccoutils.lafabrique.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.file.FileFilter;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.file.command.FileCmd;
import org.capcaval.ccoutils.lafabrique.AbstractProject;
import org.capcaval.ccoutils.lang.ArrayTools;


public class CommandCompile {
	
	public static CommandResult compile(AbstractProject proj){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		cleanProductionDirectory(proj.productionDirPath);
		FileSeekerResult result = null;
		
		JavaCompiler.CompilationTask task = null;
		
		try {
			Path[] sourcePathArray = computeAllSourcePath(proj.sourceList, proj.packageNameList);
			// get all teh source file from the given directories
			result = FileTool.seekFiles("*.java", sourcePathArray);
	
	        String classpath=System.getProperty("java.class.path");
	        Path[] libPathArray = FileTool.getFileSFromNamesAndRootDirs(proj.libDirList.toArray(new Path[0]), proj.libList);
	        String fullpath= classpath + ":.:" + ArrayTools.toStringWithDelimiter(':', libPathArray);
	        System.out.println("  ******** compile path " + fullpath);
	        
	        
	        FileCmd.makeDir.name(proj.outputBinPath.toString()).execute();
	        
	        List<String> optionList =  ArrayTools.newArrayList(
	        		"-g",
	        		"-classpath",fullpath,
	        		"-d", proj.outputBinPath.toString());
	        
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
			
		CommandResult commandResult = null;
		
		// copy all the non java file
		copyAllNonJavaSource(proj);
		
		if (isSuccessFull == true) {
			String message = Arrays.toString(result.getFileList());
			message = message + "\nCompilation is successful";
			commandResult = new CommandResult(true, message);
		}else{
			commandResult = new CommandResult(false, "Fail");
		}
		
		return commandResult;
    }

	private static void cleanProductionDirectory(Path productionDirPath) {
		try{
			// delete the production if existing
			FileTool.deleteFile( productionDirPath);
			// recreate it
			Files.createDirectories( productionDirPath);}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void copyAllNonJavaSource(AbstractProject proj) {
		FileFilter fileFilter = newNonJavaFilter();
		
		for(Path path : proj.sourceList){
			for(String packageName : proj.packageNameList){
				String packagePath = packageName.replace(".","/");
				Path p = Paths.get(path.toString()+"/" + packagePath);
				// let's copy
				FileTool.copy( p, proj.outputBinPath, path, fileFilter);
			}
		}
	}
	
	private static FileFilter newNonJavaFilter(){
		FileFilter filter = new FileFilter() {
			@Override
			public boolean isFileValid(Path path) {
				// do not copy java file
				return (path.toString().endsWith(".java")==false);
				}
			};
		return filter;
	}

	private static Path[] computeAllSourcePath(Path[] sourceList, String[] packageNameList) {
		List<Path> list = new ArrayList<>();
		for(Path path : sourceList){
			for(String packageName : packageNameList){
				String packagePath = packageName.replace(".","/");
				Path p = Paths.get(path.toString()+"/" + packagePath);
				list.add(p);
			}
		}
		
		return list.toArray(new Path[0]);
	}
}
