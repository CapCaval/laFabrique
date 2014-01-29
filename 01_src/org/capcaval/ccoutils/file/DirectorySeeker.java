package org.capcaval.ccoutils.file;

import java.io.File;

import com.sun.xml.internal.ws.util.StringUtils;

public class DirectorySeeker {

	public static FileSeekerResult seekDirectory(String rootDirStr, String containString) {
		File root = new File(rootDirStr);
		File[] fileArray = root.listFiles();
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
