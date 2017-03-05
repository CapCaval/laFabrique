package org.capcaval.lafabrique.lang;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.JVM.JVMTypeEnum;
import org.capcaval.lafabrique.lang.SystemTools.OSType;

public class JVMTools {
	
	static public JVM[] findJDK(Path path) {
		List<JVM> jvmList = new ArrayList<>();
		if (path.toFile().isDirectory() == false) {
			path = path.getParent();
		}

		// by default the linux name is used
		String javaExeName = "java";

		if (SystemTools.getOSType() == OSType.Windows) {
			javaExeName = "java.exe";
		}

		FileSeekerResult result = FileTools.seekFiles(javaExeName, false, path);
		for (Path resultPath : result.getPathList()) {
			int size = resultPath.getParent().getNameCount();
			if (resultPath.getName(size - 1).toString().equals("bin")
					&& (resultPath.getName(size - 2).toString().equals("jre") == false)) { // do
																							// not
																							// get
																							// jre
																							// inside
																							// jdk
				// String javaName = resultPath.getName(size-2).toString();
				JVM jvm = getJVM(resultPath.subpath(0, size - 1));
				if (jvm != null) {
					jvmList.add(jvm);
				}
			}
		}
		return jvmList.toArray(new JVM[0]);
	}
	
	static public JVM getSystemJDK(){
		String javaHome = System.getProperty("java.home");
		if(javaHome.endsWith("/jre")){
			javaHome = javaHome.substring(0, javaHome.length()-4);
		}
		
		return JVMTools.getJVM(Paths.get(javaHome));
	}

	static public JVM getSystemJVM(){
		String javaHome = System.getProperty("java.home");
		return JVMTools.getJVM(Paths.get(javaHome));
	}

	
	
	static public JVM getJVM(Path path){
		int size = path.getParent().getNameCount();
		String javaName = path.getName(size).toString();
		
		String typeStr = javaName.substring(0, 3);
		JVM jvm = null;
		JVMTypeEnum type = null;
		
		if(typeStr.toLowerCase().equals("jre")){
			type = JVMTypeEnum.JRE;
		}else if(typeStr.toLowerCase().equals("jdk")){
			type = JVMTypeEnum.JDK;
		}
		
		// case of jre inside a jdk
		if(javaName.equals("jre")){
			javaName = javaName + (path.getName(size-1).toString().substring(3));
		}
		
		String versionStr = javaName.substring(3);
		
		// if the type is not found it is not a real jvm
		if(type != null){
			Version version = null;
			try{
				version = Version.factory.newVersion(versionStr);}
			catch(Exception e){
				e.printStackTrace();
			}
			jvm = new JVM(path, javaName, version, type);
		}
		
		return jvm;
	}
}
