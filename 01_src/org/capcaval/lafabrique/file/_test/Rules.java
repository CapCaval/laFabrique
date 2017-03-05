package org.capcaval.lafabrique.file._test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Rules {
	public List<Path> rootPathList = new ArrayList<>();
	public List<Path> excludePathList = new ArrayList<>();
	public String pattern;
	
	public Rules excludeDirContaining(String pathStr) {
		Path p = Paths.get(pathStr);
		this.excludePathList.add(p);
		
		return this;
	}
	
	public Rules addRootDir(String pathStr) {
		this.rootPathList.add(Paths.get(pathStr));
		
		return this;
	}
	
	public Rules addExcludePath(String pathStr) {
		this.excludePathList.add(Paths.get(pathStr));
		
		return this; 
	}
	
	public Rules withPattern(String pattern) {
		this.pattern = pattern;
		
		return this; 
	}
}
