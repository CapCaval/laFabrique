package org.capcaval.lafabrique.compiler;

import java.nio.file.Path;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.compiler._impl.CompileResult;
import org.capcaval.lafabrique.compiler._test.ObjectAndResult;
import org.capcaval.lafabrique.lang.JVM;

public interface JavaBuilder {

	public CompileResult compile(String[] classArrayToBeCompiled, String... libPathArray);
	public CompileResult compile(Path[] classPathArrayToBeCompiled, Path... libPathArray);
	public CompileResult compile(String classArrayToBeCompiled, String... libPathArray);
	public CompileResult compile(Path classPathArrayToBeCompiled, Path... libPathArray);

	public CommandResult compileAll(String[] srcDirArray, String... libPathArray);
	public CommandResult compileAll(Path[] srcDirArray, Path... libPathArray);
	public CommandResult compileAll(String srcDirArray, String... libPathArray);
	public CommandResult compileAll(Path srcDirArray, Path... libPathArray);
	
	public JVM getJVM();
	public Path getOutputBinDir();
	public <T>ObjectAndResult<T> compileAndGet(Class<T> type, Path rootSrcPath, Path fileToLoad);
}
