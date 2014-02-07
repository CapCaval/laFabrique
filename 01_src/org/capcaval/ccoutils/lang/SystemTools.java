package org.capcaval.ccoutils.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.capcaval.ccoutils.file.DirectorySeeker;
import org.capcaval.ccoutils.file.FileSeekerResult;


public class SystemTools {

	public enum OSType {Windows, Linux};
	
	private static String osName = System.getProperty("os.name").toLowerCase();
	private static String javaVersion = System.getProperty("java.version");
	
	public static OSType getOSType() {
		OSType type = OSType.Linux;
		
		if(osName.contains("win")){
			type = OSType.Windows;
		}
		
		return type;
	}
	
	public static String readConsoleInput(String question) {
		// display the question
		System.out.println(question);
		
		String message = null;
	    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	    
	    try {
			message = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		return message;
	}

	public static Class<?> getCallerType(){
		// the default call level is 3
		return getCallerType(3);
	}
	
	public static String getCurrentFullMethodName(){
		// get the stack to see who called this static method
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		// get the class name of the caller
		String className = stackTrace[2].getClassName();

		// get the method of the caller
		String methodName = stackTrace[2].getMethodName();
		
		return className + "." + methodName + "()";
	}
	
	public static Class<?> getCallerType(int callLevel){
		// get the stack to see who called this static method
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		
		// get the class name of the caller
		String className = stackTrace[callLevel].getClassName();
		
		// get the path of the calling class
		Class<?> type = null;
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try {
			type =  classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return type;
	}
	
	public static Version getCurrentJavaVersion(){
		return Version.factory.newVersion(SystemTools.javaVersion);
	}

	public static JDKInstallationInfo getJDKInstallationInfo() {
		JDKInstallationInfo foundJdk = null;
		// first get the JAVA_HOME system property if set
		String javaHome = System.getenv("JAVA_HOME");
		
		// check if javac is accessible
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		// first use the path
		if(compiler != null){
			// retrieve the version
			Set<SourceVersion> set = compiler.getSourceVersions();
		}
		// use the java home variable if path is not set
		else if ((javaHome!=null)&&(javaHome.length()==0)){
			foundJdk = new JDKInstallationInfo(Paths.get(javaHome));
		}
		else{
			// javac is accessible inside the path so try to get it from the java home variable
			foundJdk = getJDKInstallationInfoFromJavaHome();
		}
		
		
		return foundJdk;
	}	
	public static JDKInstallationInfo getJDKInstallationInfoFromJavaHome() {
		// get the current jre path string
		String jrePathStr = System.getProperty("java.home");		
		// get the path format
		Path jrePath = Paths.get(jrePathStr);
		// usually jre and jdk share the same parent directory
		Path parent = jrePath.getParent();
		// get the sub directory with name containing jdk
		FileSeekerResult result = DirectorySeeker.seekDirectory("jdk", parent.toFile());

		// build all the java installation information
		List<JDKInstallationInfo> jdkList = new ArrayList<>();
		for(Path path : result.getPathList()){
			jdkList.add(new JDKInstallationInfo(path));
		}
		
		// get the highest version if any
		JDKInstallationInfo higestJDK = jdkList.get(0);
		for(JDKInstallationInfo jdk : jdkList){
			if(jdk.version.isHigherVersionThan(higestJDK.version)){
				higestJDK=jdk;
			}
		}
		
		return higestJDK;
	}
}
