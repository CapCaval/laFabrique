package org.capcaval.lafabrique.compiler._impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.compiler.JavaBuilder;
import org.capcaval.lafabrique.compiler._test.ObjectAndResult;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.JVM;
import org.capcaval.lafabrique.lang.JVMTools;
import org.capcaval.lafabrique.lang.SystemTools;

public class JavaBuilderImpl implements JavaBuilder {

	protected JVM jvm;
	private Path outputBinDir;

	public JavaBuilderImpl(){
		this.init(Paths.get("10_bin"));
	}
	
	public JavaBuilderImpl(Path outputBinDir){
		this.init(outputBinDir);
	}
	
	public JavaBuilderImpl(Path jdkPath, Path outputBinDir) {
		this.init(jdkPath, outputBinDir);
	}
	
	public JavaBuilderImpl(JVM jdk, Path outputBinDir) {
		this.init(jdk, outputBinDir);
	}
	
	protected void init(Path outputBinDir){
		// get the current JDK if any
		JVM jdk = SystemTools.getJDKInstallationInfo();
		
		this.init(jdk, outputBinDir);
	}
	
	protected void init(Path jdkPath, Path outputBinDir) {
		JVM jvm = JVMTools.getJVM(jdkPath);
		if( jvm == null){
			new RuntimeException("[laFabrique] Can not find JVM at " + jdkPath.toAbsolutePath().toString());}
		
		this.init(jvm, outputBinDir);
	}
	
	protected void init(JVM jdk, Path outputBinDir) {
		this.jvm = jdk;
		if( this.jvm == null){
			new RuntimeException("[laFabrique] JVM Can not be null");
		}
		
		this.outputBinDir = outputBinDir;
		if( this.outputBinDir== null){
			new RuntimeException("[laFabrique] outputBinDir Can not be null");
		}
	}

	@Override
	public CompileResult compile(String[] classArrayToBeCompiled, String... libPathArray) {
		String classpath = System.getProperty("java.class.path");
		classpath=".";
		classpath= "."+  
				File.pathSeparator + ArrayTools.toStringWithDelimiter(File.pathSeparatorChar, libPathArray)+
				File.pathSeparator + classpath;
		
		if(FileTools.isFileExist(this.outputBinDir) == false){
			// create it
			FileTools.createDir(this.outputBinDir);
		}
		
		String[] paramArray = ArrayTools.newArray("-g", "-classpath", classpath, "-d", this.outputBinDir.toAbsolutePath().toString());
		paramArray = ArrayTools.concat(paramArray, classArrayToBeCompiled);
		String str = this.jvm.getPath().toAbsolutePath().resolve("bin/javac") + " " +this.jvm.getPath() + " " + classpath;
		str = str + "\n" + SystemTools.execute(this.jvm.getPath().toAbsolutePath().resolve("bin/javac"), this.jvm.getPath(), paramArray);
		
		return new CompileResult(str);
	}
	
	@Override
	public JVM getJVM() {
		return this.jvm;
	}

	@Override
	public CompileResult compile(Path[] classPathArrayToBeCompiled, Path... libPathArray) {
		String[] pathStrArray = new String[classPathArrayToBeCompiled.length];
		
		for(int i=0; i<classPathArrayToBeCompiled.length; i++){
			pathStrArray[i] = classPathArrayToBeCompiled[i].toAbsolutePath().toString();
		}
		
		String[] libPatHStringArray = new String[libPathArray.length];
		
		for(int i=0; i<libPathArray.length; i++){
			libPatHStringArray[i] = libPathArray[i].toAbsolutePath().toString();
		}
		
		return this.compile(pathStrArray, libPatHStringArray);
	}

	@Override
	public CommandResult compileAll(String[] srcDirArray,String... libPathArray) {
		CommandResult cr = new CommandResult();
		
		FileSeekerResult result = null;
		
		try {
			// get all the source file from the given directories
			result = FileTools.seekFiles("*.java", srcDirArray);
			
			cr.addMessage(Type.INFO, "Found " + result.getFileList().length + " classes inside " + Arrays.toString(result.getFileList()));
			this.compile(result.getPathList());
			
		}catch(Exception e){
			cr.addErrorMessage("Compilation failed", e);
		}
		
		return cr;
	}

	@Override
	public CommandResult compileAll(Path[] srcDirArray, Path... libPathArray) {
		String[] pathStrArray = new String[srcDirArray.length];
		
		for(int i=0; i<srcDirArray.length; i++){
			pathStrArray[i] = srcDirArray[i].toAbsolutePath().toString();
		}
		
		return this.compileAll(pathStrArray);
	}

	@Override
	public <T> ObjectAndResult<T> compileAndGet(Class<T> type, Path rootSrcPath, Path fileToLoad) {
		
		CompileResult result = this.compile(rootSrcPath.resolve(fileToLoad));
		T obj = ClassTools.getFileNewInstance(
				fileToLoad.toString(),  // get file inisde the bin
				this.outputBinDir.toString());
		
		return new ObjectAndResult<T>( obj, result);
	}

	@Override
	public CompileResult compile(String classArrayToBeCompiled, String... libPathArray) {
		return compile( new String[]{classArrayToBeCompiled}, libPathArray);
	}

	@Override
	public CompileResult compile(Path classPathArrayToBeCompiled, Path... libPathArray) {
		return compile( new Path[]{classPathArrayToBeCompiled}, libPathArray);
	}

	@Override
	public CommandResult compileAll(String srcDirArray, String... libPathArray) {
		return compileAll( new String[]{srcDirArray}, libPathArray);
	}

	@Override
	public CommandResult compileAll(Path srcDirArray, Path... libPathArray) {
		return compileAll( new Path[]{srcDirArray}, libPathArray);
	}

	@Override
	public Path getOutputBinDir() {
		return this.outputBinDir;
	}
}
