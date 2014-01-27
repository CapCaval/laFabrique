package org.capcaval.ccoutils.lafabrique.command;

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
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.common.CommandResult.Type;
import org.capcaval.ccoutils.file.PathFilter;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.file.command.FileCmd;
import org.capcaval.ccoutils.lafabrique.AbstractProject;
import org.capcaval.ccoutils.lang.ArrayTools;
import org.capcaval.ccoutils.lang.StringMultiLine;


public class CommandCompile {
	
	public static CommandResult compile(AbstractProject proj){
		StringMultiLine returnedMessage = new StringMultiLine();
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		cleanProductionDirectory(proj.productionDirPath);
		FileSeekerResult result = null;
		
		JavaCompiler.CompilationTask task = null;
		
		try {
			Path[] sourcePathArray = computeAllSourcePath(proj.sourceDirList, proj.packageNameList);
			// get all teh source file from the given directories
			result = FileTools.seekFiles("*.java", sourcePathArray);
			
			returnedMessage.addLine("[laFabrique] INFO  : Found " + result.getFileList().length + " classes");
	
	        String classpath=System.getProperty("java.class.path");
	        Path[] libPathArray = FileTools.getFileSFromNamesAndRootDirs(proj.libDirList.toArray(new Path[0]), proj.libList);
	        String fullpath= classpath + ":.:" + ArrayTools.toStringWithDelimiter(':', libPathArray);
	        
	        String binDir = proj.productionDirPath.toString() + "/" + proj.tempProdSource;
	        
	        FileCmd.makeDir.name(binDir).execute();
	        
	        List<String> optionList =  ArrayTools.newArrayList(
	        		"-g",
	        		"-classpath",fullpath,
	        		"-d", binDir);
	        
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
		
		
		if (isSuccessFull == true) {
			// copy all the non java file
			copyAllNonJavaSource(proj);
			
			returnedMessage.addLine("[laFabrique] INFO  : Compilation is successful");
			commandResult = new CommandResult(returnedMessage.toString());
		}else{
			returnedMessage.addLine("[laFabrique] ERROR  : Compilation failed");
			commandResult = new CommandResult(Type.ERROR, returnedMessage.toString());
		}
		
		return commandResult;
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

	private static void copyAllNonJavaSource(AbstractProject proj) {
		PathFilter fileFilter = newNonJavaFilter();
		
		Path destBinDir = proj.productionDirPath.resolve(Paths.get(proj.tempProdSource));
		
		for(Path path : proj.sourceDirList){
			for(String packageName : proj.packageNameList){
				String packagePath = packageName.replace(".","/");
				Path p = Paths.get(path.toString()+"/" + packagePath);
				// let's copy
				FileTools.copy( p, destBinDir, path, fileFilter);
			}
		}
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
