package org.capcaval.lafabrique.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.capcaval.lafabrique.file.DirectorySeeker;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;

public class SystemTools {

	public enum OSType {
		Windows, Linux
	};

	private static String osName = System.getProperty("os.name").toLowerCase();
	private static String javaVersion = System.getProperty("java.version");

	public static OSType getOSType() {
		OSType type = OSType.Linux;

		if (osName.contains("win")) {
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

	public static Class<?> getCallerType() {
		// the default call level is 3
		return getCallerType(3);
	}

	public static String getCurrentFullMethodName() {
		// get the stack to see who called this static method
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		// get the class name of the caller
		String className = stackTrace[2].getClassName();

		// get the method of the caller
		String methodName = stackTrace[2].getMethodName();

		return className + "." + methodName + "()";
	}

	public static Class<?> getCallerType(int callLevel) {
		// get the stack to see who called this static method
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		// get the class name of the caller
		String className = stackTrace[callLevel].getClassName();

		// get the path of the calling class
		Class<?> type = null;
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try {
			type = classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return type;
	}

	public static Version getCurrentJavaVersion() {
		return Version.factory.newVersion(SystemTools.javaVersion);
	}

	public static JVM getJDKInstallationInfo() {
		JVM foundJdk = null;
		// first get the JAVA_HOME system property if set
		String java_Home = System.getenv("JAVA_HOME");

		// check if javac is accessible
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// first use the path
		if (compiler != null) {
			String javaHome = System.getProperty("java.home");
			String javaVersion = System.getProperty("java.version");
			if(javaHome.endsWith("/jre")){
				javaHome = javaHome.substring(0, javaHome.length()-4);
			}
			
			foundJdk = JVMTools.getJVM(Paths.get(javaHome)); 
		}
		// use the java home variable if path is not set
		else if ((java_Home != null) && (java_Home.length() == 0)) {
			foundJdk = JVMTools.getJVM(Paths.get(java_Home));
		} else {
			// javac is accessible inside the path so try to get it from the
			// java home variable
			foundJdk = getJDKInstallationInfoFromJavaHome();
		}

		return foundJdk;
	}

	public static JVM getJDKInstallationInfoFromJavaHome() {
		// get the current jre path string
		String jrePathStr = System.getProperty("java.home");
		// get the path format
		Path jrePath = Paths.get(jrePathStr);
		if(jrePathStr.endsWith("/bin")||jrePathStr.endsWith("/jre")){
			jrePath = jrePath.getParent();
		}
		
		// usually jre and jdk share the same parent directory
		Path parent = jrePath.getParent();
		// get the sub directory with name containing jdk
		FileSeekerResult result = DirectorySeeker.seekDirectory("jdk", parent.toFile());

		// build all the java installation information
		List<JVM> jdkList = new ArrayList<JVM>();
		for (Path path : result.getPathList()) {
			jdkList.add(JVMTools.getJVM(path));
		}

		// get the highest version if any
		JVM higestJDK = null;
		if (jdkList.size() > 0) {
			higestJDK = jdkList.get(0);
			for (JVM jdk : jdkList) {
				if (jdk.version.isThisVersionHigherThan(higestJDK.version)) {
					higestJDK = jdk;
				}
			}
		}

		return higestJDK;
	}

	public static void addPathToClassPath(String path){
		try {
			File file = new File(path);
			URI uri = file.toURI();
			URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<URLClassLoader> urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { uri.toURL() });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	public static Jdk getJdkFrom(String... variableList){
//		Jdk jdk=null;
//		for(String var : variableList){
//			
//			String path = System.getenv(var);
//			//check that the path exist
//			if(FileTools.isFileExist(path)==true){
//				System.setProperty("java.home", path);
//				SystemTools.addPathToClassPath(path+"/lib/tools.jar");
//			}
//			
//			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//			
//			if(compiler != null){
//				jdk = new Jdk(Paths.get(path),compiler, var);
//				break;
//			}
//		}
//		return jdk;
//	}

	public static String executeClassInAnotherJVM(Class<?> type, String... args) {
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
	
	public static String execute(Path fileToExec, Path contextPath, String... parameterList){
		StringMultiLine returnStr = new StringMultiLine();

		try {
			List<String> cmdList = new ArrayList<>();
			
			// get first the exe file full path
			String fileExe = fileToExec.toString();
			
			if(SystemTools.getOSType() == OSType.Windows){
				if(fileExe.endsWith(".sh")){
					StringTools.removeEnd(".sh", ".bat");
				}
				else{
					fileExe = fileExe + ".exe";
				}
			}
			
			cmdList.add(fileExe);
			ListTools.concat(cmdList, parameterList);
			
			ProcessBuilder builder = new ProcessBuilder(cmdList);
			builder.directory(contextPath.toFile());
			builder.redirectErrorStream(true);
			Process p = builder.start();

			p.waitFor();

			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				returnStr.addLine(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnStr.toString();
	}
	
	
	public static boolean isJavaVersionAboveVersion(String versionStr) {
		Version comparedVersion = Version.factory.newVersion(versionStr);
		boolean result = getJDKInstallationInfo().version.isThisVersionHigherThan(comparedVersion);
		
		return result;
	}

	public static boolean isJavaVersionBelowVersion(String versionStr) {
		Version comparedVersion = Version.factory.newVersion(versionStr);
		boolean result = getJDKInstallationInfo().version.isThisVersionLowerThan(comparedVersion);
		
		return result;
	}

	public static String getUserName() {
		return System.getProperty("user.name");
	}
}
