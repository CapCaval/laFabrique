package org.capcaval.lafabrique.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.compiler._impl.JavaBuilderImpl;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.command.FileCmd;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.JVM;
import org.capcaval.lafabrique.lang.JVMTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.project.Project;

public class CompilerTools {
	
//	public static<T> T compileClass(Class<? extends T> type, String binDir, String rootDir) throws Exception{
//		// build the file name from the type
//		String fileName = ClassTools.getFileNameFromType(type);
//		
//		
//		return compileClass(rootDir, fileName, binDir);
//	}
	
//	public static<T> T compileClass(/*Class<? extends T> type,*/ String root, String source, String outputDir) throws Exception{
//		compile(root, source, outputDir);
//
//		Class<?> c = null;
//		
//		SystemClassLoader scm = new SystemClassLoader();
//		// scm.addURL(binDir.toString(),"./10_bin");
//		scm.addURL(outputDir);
//		String name = source.replace('/', '.').replace(".java", "");
//		
//		c = scm.loadClass(name);
//		
//		T instance = null;
//		if(c!=null){
//			instance = (T)c.newInstance();}
//		
//		scm.close();
//		
//		return instance;
//	}


//	public static<T> T compile(Class<? extends T> type, String sourceFileName)throws Exception{
//		
//		String fullClassName = getFullClassNameFromFile(sourceFileName);
//		
//		String classFileName = fullClassName.replace('.', '/') + ".java";
//		
//		String rootDir = sourceFileName.replace(classFileName, "");
//		
//		T obj = compile( type, rootDir, classFileName, "ccBinTemp");
//		
//		return obj;
//	}
	

	
//	public static void compile( String root, String source, String outputDir) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
//		
//		File rootDir = new File(root);
//		File binDir = new File(outputDir);
//		File sourceFile = new File(rootDir, source);
//		
//		// check if the bin directory exist
//		if(binDir.exists() == false){
//			// if not create it
//			binDir.mkdirs();
//		}
//		
//		// Compile source file.
//		try{
////			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
////			
////			if(compiler == null){
////				// jdk is not accessible with PATH
////				JdkPathInfo jdk = SystemTools.getJDKInstallationInfo();
////				System.setProperty("java.home", jdk.path.toString() + "/bin");
////				compiler = ToolProvider.getSystemJavaCompiler();
////			}
////			
////			if(compiler ==null){
////				// use java home system property
////				String javaHome = System.getenv("JAVA_HOME");
////				if((javaHome!=null)&&(javaHome.length()>1)){
////					//System.setProperty("java.home", javaHome);
////					SystemTools.addPathToClassPath(javaHome+"/lib/tools.jar");
////					compiler = ToolProvider.getSystemJavaCompiler();
////				}
////			}
////			
////			if(compiler==null){
////				JdkPathInfo jdk = SystemTools.getJDKInstallationInfo();
////				System.setProperty("java.home", jdk.path.toString());
////				
////				SystemTools.addPathToClassPath(jdk.toString()+"/lib/tools.jar");
////				
////				compiler = ToolProvider.getSystemJavaCompiler();
////			}
//
//			JavaCompiler compiler = getJavaCompiler();
//			
//			Path libPath = Paths.get("02_lib/laFabrique.jar").toAbsolutePath();
//			Path binPath = Paths.get("10_bin").toAbsolutePath();
//			
//			String cp = "../10_bin"+ File.pathSeparator + binPath.toString() + File.pathSeparator + libPath.toString();
//			
//			if(compiler != null){
//				compiler.run(null, null, null, 
//					"-g", // debug
//					"-cp", cp, // classpath
//					"-d", outputDir, 
//					sourceFile.getPath());
//			}
//			else{
//				System.out.println("[ccOutils] ERROR : JDK cannot be found. Please install a version 1.7 or above. ccOutils uses the PATH to find JDK firstly and secondly JAVA_HOME variable.");
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

	
	public static String getFullClassNameFromFile(String sourceFileName) throws IOException{
		String fileStr = FileTools.readStringfromFile(sourceFileName);
		String[] lineArray = fileStr.split("\n");
		
		String packageName = "";
		String className = "";
		
		for(String line : lineArray){
			if(line.contains("package")){
				packageName = line.replaceAll("package", "");
				packageName = packageName.replaceAll(" ", "");
				packageName = packageName.replaceAll("\t", "");
				packageName = packageName.replaceAll(";", "");
			}
			if(line.contains("class")){
				String temp  = line.split("class")[1];
				for(String str : temp.split(" ")){
					if(str.equals("") == false){
						className = str;
						break;
					}
				}
				
				break;
			}
		}
		String fullName = packageName + "." + className;

		return fullName;
	}
	
	static public JavaCompiler getJavaCompiler(){
		return getJavaCompiler(null);
	}
	
	static public JavaCompiler getJavaCompiler(Project proj){
		// check out if a jdk has been set
		if((proj != null)&&(proj.jdkVersion != null)){
			SystemTools.addPathToClassPath(proj.jdkVersion.getPath().toString()+"/lib/tools.jar");
		}
		
		// get compiler by the the default way. It used the system path.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		// if the default method from classpath does not work 
		// try the following other methods
		if(compiler==null){
			JVM jdk = SystemTools.getJDKInstallationInfo();
			System.setProperty("java.home", jdk.toString());
			// special case add tools.jar from the jdk
			SystemTools.addPathToClassPath(jdk.getPath().toString()+"/lib/tools.jar");
			
			compiler = ToolProvider.getSystemJavaCompiler();
		}
		
		// method by getting the JAVA_HOME system variable
		if(compiler ==null){
			// use java home system property
			String javaHome = System.getenv("JAVA_HOME");
			if((javaHome!=null)&&(javaHome.length()>1)){
				System.setProperty("java.home", javaHome);
				
				SystemTools.addPathToClassPath(javaHome+"/lib/tools.jar");
				
				compiler = ToolProvider.getSystemJavaCompiler();
			}
		}

		return compiler;
	}
	
	public static CommandResult compileAll(String binDirStr, String... sourceDirArray) {
		CommandResult cr = new CommandResult();
		
		JavaCompiler compiler = CompilerTools.getJavaCompiler();

		if(compiler ==null){
			cr.addErrorMessage("CANNOT find JDK. Please install a 1.7 version or above.");
			return cr;
		}
		
		//cleanProductionDirectory(binDirStr);
		FileSeekerResult result = null;
		
		JavaCompiler.CompilationTask task = null;
		
		try {
			// get all the source file from the given directories
			result = FileTools.seekFiles("*.java", sourceDirArray);
			
			cr.addMessage(Type.INFO, "Found " + result.getFileList().length + " classes inside " + Arrays.toString(result.getFileList()));
	
	        String classpath=System.getProperty("java.class.path");
	        Path[] libPathArray = FileTools.getFileSFromNamesAndRootDirs(sourceDirArray, result.getStringFileList());
	        String fullpath= classpath + File.pathSeparator + "."+  File.pathSeparator + ArrayTools.toStringWithDelimiter(File.pathSeparatorChar, libPathArray);
	        
	        FileCmd.makeDir.name(binDirStr).execute();
	        
	        List<String> optionList =  ArrayTools.newArrayList(
	        		"-g",
	        		"-classpath",fullpath,
	        		"-d", binDirStr);
	        
	        StandardJavaFileManager sfm = compiler.getStandardFileManager(null, null, null);
	        Iterable<? extends JavaFileObject> fileObjects = sfm.getJavaFileObjects(result.getStringFileList());
        
	        task = compiler.getTask(
	        		null, null, null,
	        		optionList,null,fileObjects);
	        
			if(task.call() == true){
				cr.addMessage(Type.INFO, "Compilation is successful");
			}else{
				cr.addErrorMessage("Compilation failed");	
			}
			
			sfm.close();
		}catch(Exception e){
			cr.addErrorMessage("Compilation failed", e);
			// keep going
		}
		
		return cr;

	}
	
	
	public static String compile(Class<?> type, String... args) {
		String cpStr = System.getProperty("java.class.path");
		
		String[] cmdArray = new String[] {
				  "java", 
				  "-classpath", cpStr, 
				  type.getCanonicalName()};
		
		cmdArray = ArrayTools.concat(cmdArray, args);
		StringMultiLine returnStr = new StringMultiLine();
		
		try {
			ProcessBuilder builder = new ProcessBuilder(cmdArray);
			builder.redirectErrorStream(true);
			Process p = builder.start();

			p.waitFor();
			
		    InputStream is = p.getInputStream();
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
			
		    String line;
		    while((line = br.readLine()) != null) {
		    	returnStr.addLine(line);
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr.toString();
	}

	public static JavaBuilder newJavaBuilder(Path jdkPath, Path outputBinDir) {
		return new JavaBuilderImpl(jdkPath, outputBinDir);
	}

	public static JavaBuilder newJavaBuilder(Path outputBinDir) {
		return new JavaBuilderImpl(outputBinDir);
	}

	public static JavaBuilder newJavaBuilder() {
		return new JavaBuilderImpl();
	}

	public static JavaBuilder getJavaBuilder(Project proj) {
		JVM jdk = null;
		
		// check out if a jdk has been set
		if((proj != null)&&(proj.jdkVersion != null)){
			// get the jdk set in the project class
			jdk = JVMTools.getJVM(proj.jdkVersion.getPath());
		}else{
			jdk = SystemTools.getJDKInstallationInfo();
		}
		// get java builder
		JavaBuilder javaBuilder = CompilerTools.newJavaBuilder(jdk.getPath(), proj.binDirPath);
		
		return javaBuilder;
	}


}
