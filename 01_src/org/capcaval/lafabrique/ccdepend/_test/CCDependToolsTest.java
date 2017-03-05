package org.capcaval.lafabrique.ccdepend._test;

import java.nio.file.Path;
import java.util.Arrays;

import org.capcaval.lafabrique.ccdepend.CCDependTools;
import org.capcaval.lafabrique.ccdepend.DependencyError;
import org.capcaval.lafabrique.ccdepend.meta.Item;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.StringTools;
import org.junit.Test;

public class CCDependToolsTest {

	@Test
	public void importSeekTest(){
		Path pathToAnalyse = FileTools.getLocalDirPath().resolve("sub");
		
		// source classes to analyse
		Item[] rootArray = CCDependTools.analyse(pathToAnalyse, "*.java_");
		
		StringBuilder strb = new StringBuilder();
		for(Item item : rootArray){
			String str = item.toString();
			System.out.println(str);
		}
		
		System.out.println(strb.toString());
	}

	@Test
	public void checkLoopTest(){
		Path pathToAnalyse = FileTools.getLocalDirPath().resolve("sub");
		
		// source classes to analyse
		DependencyError[] errorArray = CCDependTools.checkForDependencyLoop(pathToAnalyse, "*.java_");
		
		System.out.println(Arrays.toString(errorArray));
	}	
	
}
