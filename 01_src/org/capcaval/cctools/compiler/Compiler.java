package org.capcaval.cctools.compiler;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.capcaval.cctools.file.FileSeeker.FileSeekerResult;
import org.capcaval.cctools.file.FileTool;

public class Compiler {

	public static void main(String[] args) throws IOException {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		FileSeekerResult result = FileTool.seekFiles("*.java", Paths.get("compileTest"));

		int compilationResult = compiler.run(null, null, null, result.getStringFileList());
		if (compilationResult == 0) {
			System.out.println(Arrays.toString(result.getFileList()));
			System.out.println("Compilation is successful");
		} else {
			System.out.println("Compilation Failed");
		}
	}
}
