package org.capcaval.lafabrique.lang._test;

import java.nio.file.Paths;

import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.lang.PropertyTools;

public class PropertyToolsTest {
	
	class Test{
		int version = 123;
		String name = "toto";
	}
	
	@org.junit.Test
	public void savePropertyTest(){
		ConverterManager cm = new ConverterManager();
		PropertyTools.saveAllPersistentProperties(new Test(), Paths.get("test/propTest.properties"), cm);
	}
}
