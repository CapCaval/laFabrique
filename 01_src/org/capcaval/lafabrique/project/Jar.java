package org.capcaval.lafabrique.project;

import java.nio.file.Path;

import org.capcaval.lafabrique.lafab.LaFabTaskDesc;

public class Jar extends LaFabTaskDesc{
	public String name;
	public Path outputBinPath;
	public String[] excludedDirectoryList;
	public Path outputJar;
	public boolean isSourceIncluded;

	public void name(String name){
		this.name = name;
	}
	public void excludedDirectoryName(String... dirList){
		this.excludedDirectoryList = dirList;
	}
	public void outputBinPath(Path pathStr) {
		this.outputBinPath = pathStr;
	}
	public void outputJar(Path pathStr) {
		this.outputJar = pathStr;
	}
	public void includeSource(boolean isSourceIncluded) {
		this.isSourceIncluded = isSourceIncluded;
	}
}
