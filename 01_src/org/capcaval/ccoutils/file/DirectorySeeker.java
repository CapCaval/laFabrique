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
			System.out.println(f.getName());
			if((f.isDirectory() == true)&&(containString.contains(f.getName()) == true)){
				result.addFiles(f.toPath());
			}
		}
		
		return result;
	}

}
