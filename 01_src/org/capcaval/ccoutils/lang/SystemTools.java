package org.capcaval.ccoutils.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import junit.framework.Assert;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.file.FileTools;
import org.junit.Test;


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
		return Version.factory.newVersion(System.getProperty("java.version"));
	}

	public static JavaInstallationInfo getJavaInstallationInfo() {
		
		String jrePathStr = System.getProperty("java.home");		
	
		Path jrePath = Paths.get(jrePathStr);
		
		Path parent = jrePath.getParent();

		FileSeekerResult result = null;
		try {
			result = FileTools.seekFiles("jdk*", parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(Arrays.toString(result.getPathList()));
		
		
		return null;
	}
}
