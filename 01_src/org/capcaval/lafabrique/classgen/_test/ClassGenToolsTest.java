package org.capcaval.lafabrique.classgen._test;

import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.classgen.Attribute;
import org.capcaval.lafabrique.classgen.ClassGenTools;
import org.capcaval.lafabrique.classgen.EnumNameValue;
import org.capcaval.lafabrique.file.FileTools;

public class ClassGenToolsTest {
	
	@org.junit.Test
	public void genConfigClassTest(){
		TestClass testInstance = new TestClass();
		testInstance.name = "toto";
		testInstance.value = 5;
		
		ClassGenTools.generateClass(Paths.get("01_src"),"test.NewClass", testInstance);
		
		Assert.assertTrue(FileTools.isFileExist("01_src/test/NewClass.java"));
	}
	
	
	@org.junit.Test
	public void genConfigEnumTest(){
		TestClass testInstance = new TestClass();
		testInstance.name = "toto";
		testInstance.value = 5;
		
		ClassGenTools.generateEnum(
				Paths.get("01_src"),"test.NewEnum",
				new EnumNameValue("a"), Paths.get("abc"), 5, "toto",
				new EnumNameValue("b"), Paths.get("def"), 5, "titi",
				new EnumNameValue("c"), Paths.get("ghi"), 5, "tutu");
	
			
		Assert.assertTrue(FileTools.isFileExist("01_src/test/NewEnum.java"));
	}
	
	@org.junit.Test
	public void getAttributesClassTest(){
		TestClass testInstance = new TestClass();
		testInstance.name = "toto";
		testInstance.value = 5;
		
		Attribute<?>[] attributeArray = ClassGenTools.getAttributes(testInstance);
		
		Attribute<?> first = attributeArray[0];
		Attribute<?> second = attributeArray[1];
		Attribute<?> third = attributeArray[2];
		
		Assert.assertEquals(first.getName(), "value");
		Assert.assertEquals(first.getValue(), 5);
		Assert.assertEquals(first.getType(), int.class);

		Assert.assertEquals(second.getName(), "name");
		Assert.assertEquals(second.getValue(), "toto");
		Assert.assertEquals(second.getType(), String.class);

		Assert.assertEquals(third.getName(), "floatValue");
		Assert.assertEquals(third.getValue(), null);
		Assert.assertEquals(third.getType(), Float.class);
	}


}
