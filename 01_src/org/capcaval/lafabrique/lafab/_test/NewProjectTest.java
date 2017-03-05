package org.capcaval.lafabrique.lafab._test;

import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lafab.LaFab;

public class NewProjectTest {
	
	@org.junit.Test
	public void newProjectTest(){
		Path testPath = FileTools.getLocalDirPath();
		
		String projectName = "Toto";
		
		// first clean the tested directory
		Path path = testPath.resolve(Paths.get(projectName));
		FileTools.deleteFile(path);
		
		LaFab.main(new String[]{"newProject", projectName, testPath.toString()});
		
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/00_prj"));
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/00_prj/prj/" + projectName + ".java"));
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/01_src"));
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/02_lib"));
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/02_lib/laFabrique.jar"));
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/laFab"));
		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/laFab.bat"));
		
		
	}
}
