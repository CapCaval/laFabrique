package org.capcaval.ccoutils.lafabrique;

import java.nio.file.Path;

public class Jar {
	public String name;
	public Path outputBinPath;
	public String[] excludedDirectoryList;
	public Path outputJar;

	public void name(String name){
		this.name = name;
	}
	public void excludeDirectoryName(String... dirList){
		this.excludedDirectoryList = dirList;
	}
	public void outputBinPath(Path pathStr) {
		this.outputBinPath = pathStr;
	}
	public void outputJar(Path pathStr) {
		this.outputJar = pathStr;
	}
}
