package org.capcaval.ccoutils.file;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSeekerResult{
	private List<Path> fileList = new ArrayList<>();;

	public FileSeekerResult(){
	}
	
	void addFiles(Path... fileList){
		// add files
		for(Path p : fileList){
			this.fileList.add(p);
		}
	}
	
	public File[] getFileList(){
		List<File> fileList = new ArrayList<>();
		for(Path p : this.fileList){
			fileList.add(p.toFile());
		}
		return fileList.toArray(new File[0]);
	}

	public Path[] getPathList(){
		return this.fileList.toArray(new Path[0]);
	}
	
	public String[] getStringFileList(){
		List<String> stringList = new ArrayList<>();
		
		for(Path p : this.fileList){
			stringList.add(p.toString());
		}
		return stringList.toArray(new String[0]);
	}
}
