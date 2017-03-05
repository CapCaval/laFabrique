package org.capcaval.lafabrique.lang._test;

import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.PropertyTools;
import org.capcaval.lafabrique.lang.StringTools;

public class CopyOfStreamToolsTest {
	
	class Test{
		int version = 123;
		String name = "toto";
	}
	
	@org.junit.Test
	public void savePropertyTest(){
		ConverterManager cm = new ConverterManager();
		FileTools.createAndCleanDir("test");
		
		Path path = Paths.get("test/propTest.properties");
		PropertyTools.saveAllPersistentProperties(new Test(), path, cm);
		
		String fileContain = FileTools.readStringfromFile(path);
		// remove first line as it is the date
		String result = StringTools.removeFirstLine(fileContain);
		Assert.assertEquals("", result);
	}
}
