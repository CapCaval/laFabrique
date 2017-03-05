package org.capcaval.lafabrique.project;

import java.nio.file.Path;

public class ProjectTools {

	public static Path getProdPackDir(Project project) {
		String packDir = project.name + "_"+project.version;
		Path packPath = project.productionDirPath.resolve(packDir);
		return packPath;
	}
	
	public static Path getProdJarDir(Project project){
		String jarName = project.jar.name.replace(".jar", "Jar");
		Path dirPath = project.productionDirPath.resolve(jarName);
		
		return dirPath;
	}
}
