package org.capcaval.lafabrique.lafab._test;

import org.capcaval.lafabrique.lafab.LaFab;

public class NewApplicationTest {
	
	@org.junit.Test
	public void newApplicationTest(){
		//Path testPath = FileTools.getLocalDirPath();
		
		String currentPackage = this.getClass().getPackage().toString();
		currentPackage = currentPackage.substring(8, currentPackage.length());
		String appName = currentPackage + ".CoolApp";
		
		// first clean the tested directory
//		Path path = testPath.resolve(Paths.get(projectName));
//		try {
//			FileTools.deleteFile(path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		LaFab.main(new String[]{"newApp", appName});
		
//		Assert.assertTrue(FileTools.isFileExist( path.toString() + "/00_prj"));
		
	}
}
