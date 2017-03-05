package org.capcaval.lafabrique.file._test;

import java.nio.file.Paths;

public class RulesTools {

	public static Rules addRootDir(String pathStr) {
		Rules r = new Rules();
		r.rootPathList.add(Paths.get(pathStr));
		
		return r;
	}
	
	public static Rules addExcludePath(String pathStr) {
		Rules r = new Rules();
		r.excludePathList.add(Paths.get(pathStr));
		
		return r; 
	}
	
	public static Rules withPattern(String pattern) {
		Rules r = new Rules();
		r.pattern = pattern;
		
		return r; 
	}
}
