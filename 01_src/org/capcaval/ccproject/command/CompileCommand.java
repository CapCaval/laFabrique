package org.capcaval.ccproject.command;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccproject.AbstractProject;

public class CompileCommand {

	public CommandResult compile(AbstractProject proj){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		FileSeekerResult result = null;
		try {
			result = FileTool.seekFiles("*.java", proj.sourceList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		CommandResult commandResult = null;
		int compilationResult = compiler.run(null, null, null, result.getStringFileList());
		if (compilationResult == 0) {
			String message = Arrays.toString(result.getFileList());
			message = message + "\nCompilation is successful";
			commandResult = new CommandResult(true, message);
		}else{
			commandResult = new CommandResult(false, "Error");
		}
		
		return commandResult;
	}
}
