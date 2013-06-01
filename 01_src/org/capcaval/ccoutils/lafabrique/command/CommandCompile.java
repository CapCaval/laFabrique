package org.capcaval.ccoutils.lafabrique.command;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.file.command.FileCmd;
import org.capcaval.ccoutils.lang.ArrayTools;
import org.capcaval.ccproject.AbstractProject;


public class CommandCompile {
	
	public static CommandResult compile(AbstractProject proj){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		try{
			// delete the production if existing
			FileTool.deleteFile(proj.productionDirPath);
			// recreate it
			Files.createDirectories(proj.productionDirPath);}
		catch(Exception e){
			e.printStackTrace();
		}
		
		FileSeekerResult result = null;
		try {
			result = FileTool.seekFiles("*.java", proj.sourceList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
        String classpath=System.getProperty("java.class.path");
        String fullpath=classpath+":.:" + ArrayTools.toStringWithDelimiter(':', proj.libList);
        System.out.println(fullpath);
        
        
        FileCmd.makeDir.name(proj.outputBinPath.toString()).execute();
        
        List<String> optionList =  ArrayTools.newArrayList(
        		"-classpath",fullpath,
        		"-d", proj.outputBinPath.toString());
        
        StandardJavaFileManager sfm = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileObjects = sfm.getJavaFileObjects(result.getStringFileList());
        JavaCompiler.CompilationTask task = compiler.getTask(
        		null, null, null,
        		optionList,null,fileObjects);

        try {
			sfm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean isSuccessFull = task.call();
		CommandResult commandResult = null;
		
		if (isSuccessFull == true) {
			String message = Arrays.toString(result.getFileList());
			message = message + "\nCompilation is successful";
			commandResult = new CommandResult(true, message);
		}else{
			commandResult = new CommandResult(false, "Fail");
		}
		
		try {
			result = FileTool.seekFiles("*", proj.sourceList);
			for(Path path: result.getPathList()){
				if(path.endsWith(".java") == false){
					//copy the file to the bin directory
					OutputStream out = null;
					Files.copy(path, out);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return commandResult;
    }
}
