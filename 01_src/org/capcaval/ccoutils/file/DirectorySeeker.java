package org.capcaval.ccoutils.file;

import java.io.File;

public class DirectorySeeker {

	public static FileSeekerResult seekDirectory(String containString, String rootDirStr) {
		File root = new File(rootDirStr);
		return seekDirectory(containString, root);
	}
	public static FileSeekerResult seekDirectory(String containString, File rootDir) {
		File[] fileArray = rootDir.listFiles();
		FileSeekerResult result = new FileSeekerResult();
		
		// check all sub directories
		for(File f : fileArray){
			if(f.isDirectory() == true){
				String dirName = f.getName();
				if(dirName.contains(containString) == true){
					result.addFiles(f.toPath());
				}
			}
		}
		
		return result;
	}

}
