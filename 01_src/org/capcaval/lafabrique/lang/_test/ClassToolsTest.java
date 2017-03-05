/*
Copyright (C) 2012 by CapCaval.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package org.capcaval.lafabrique.lang._test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.compiler.CompilerTools;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.Version;



public class ClassToolsTest {
	
	public class DefaultClass{ public void emptyMethod(){}}
	public class NoParameterCtorClass{
		public NoParameterCtorClass(){}
	}
	public class OnlyParameterCtorClass{
		public OnlyParameterCtorClass(int param1, int param2){}
	}
	public class MixParameterCtorClass{
		public MixParameterCtorClass(){}
		public MixParameterCtorClass(int param1, int param2){}
	}
	
	public class InnerClazzTest{}
	
	public static class InnerStaticClazzTest{}

	
	@org.junit.Test
	public void testDifferentTypeOfClass(){
		// default constructor when undefined
		boolean isContainsNoParameterConstructor = ClassTools.isConstructorWithoutArgument(DefaultClass.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);
	
		// constructor with no parameter 
		isContainsNoParameterConstructor = ClassTools.isConstructorWithoutArgument(NoParameterCtorClass.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);
		
		// inner class constructor with no parameter 
		isContainsNoParameterConstructor = ClassTools.isConstructorWithoutArgument(InnerClazzTest.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);

		// inner class constructor with no parameter 
		isContainsNoParameterConstructor = ClassTools.isConstructorWithoutArgument(InnerStaticClazzTest.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);
		
		// constructor with with parameters 
		isContainsNoParameterConstructor = ClassTools.isConstructorWithoutArgument(OnlyParameterCtorClass.class);
		Assert.assertEquals(false, isContainsNoParameterConstructor);
		
		// constructor with mix parameters 
		isContainsNoParameterConstructor = ClassTools.isConstructorWithoutArgument(MixParameterCtorClass.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);

	}
	@org.junit.Test
	public void testGetClassFromShortName(){
		Class<?> type = ClassTools.getClassFromShortName("ClassToolsTest");
		
		Assert.assertEquals(ClassToolsTest.class, type);
	}

	@org.junit.Test
	public void testGetInnerClassFromShortName(){
		Class<?> type = ClassTools.getClassFromShortName("InnerClazzTest");
		
		Assert.assertEquals(InnerClazzTest.class, type);
	}

	@org.junit.Test
	public void testIsJunitCLass(){
		// verify class containing JUnit test
		boolean containsJUnitTest = ClassTools.doesClassContainJUnitTest(ClassToolsTest.class);
		Assert.assertTrue(containsJUnitTest);
		// verify class containing JUnit test
		containsJUnitTest = ClassTools.doesClassContainJUnitTest(MixParameterCtorClass.class);
		Assert.assertFalse(containsJUnitTest);
	}
	
	@org.junit.Test
	public void testGetClassPath(){
		Path path = ClassTools.getBinaryPath(this.getClass());
		Path resultPath = Paths.get("./11_bin/org/capcaval/ccoutils/lang/_test/ClassToolsTest.class");
		
		try {
			Assert.assertTrue(Files.isSameFile(resultPath, path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void testClassNameToFileName(){
		String filename = ClassTools.getFileNameFromClassName("org.capcaval.Test");
		
		Assert.assertEquals("org/capcaval/Test.java", filename);
	}
	
	@org.junit.Test
	public void testTypeToFileName(){
		String filename = ClassTools.getFileNameFromType(org.capcaval.lafabrique.lang.ClassTools.class);
		
		Assert.assertEquals("org/capcaval/ccoutils/lang/ClassTools.java", filename);
	}
	

	@org.junit.Test
	public void testClassNameFromFullName(){
		String fileName = ClassTools.getClassNameFromFullName(org.capcaval.lafabrique.lang.ClassTools.class.getCanonicalName());
		Assert.assertEquals("ClassTools", fileName);
		
		fileName ="SimpleClass";
		String result = ClassTools.getClassNameFromFullName(fileName);
		Assert.assertEquals(fileName, result);
	}

	@org.junit.Test
	public void testPackageNameFromFullName(){
		String packageName = ClassTools.getPackageNameFromFullName(org.capcaval.lafabrique.lang.ClassTools.class.getCanonicalName());
		Assert.assertEquals("org.capcaval.ccoutils.lang", packageName);
		
		String result = ClassTools.getPackageNameFromFullName("SimpleClass");
		Assert.assertEquals("", result);
	}
	
	
	@org.junit.Test
	public void newInstanceFromFileTestInsideCurrentCp(){
		TestMain instance = ClassTools.getFileNewInstance("org/capcaval/ccoutils/lang/_test/TestMain.java");
		Assert.assertEquals("No worries.", instance.getValue());

		try{
			instance = ClassTools.getFileNewInstance("fileThatDoesNotExist.java");
			// shall never come here
			Assert.fail();
		}catch(Exception e){
			Assert.assertTrue(true);
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void newInstanceFromFileTestInsideCustomCp(){
		
		StringMultiLine src = new StringMultiLine(
				"public class Outside extends Object{",
				"	public String toString(){ return \"Hello\";}",
				"}");
		
		FileTools.createAndCleanDir("temp");
		FileTools.writeFile("temp/src/Outside.java", src.toString());
		
		// compile
		CompilerTools.compileAll("temp/bin", "temp/src");
		Object instance = null;
		try{
			instance = ClassTools.getFileNewInstance("Outside.java", "temp/bin");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Assert.assertEquals("Hello", instance.toString());
	}
	
	@org.junit.Test
	public void mainClassTest(){
		boolean r = ClassTools.doesClassContainMain(MainClass.class);
		Assert.assertTrue(r);
		
		r = ClassTools.doesClassContainMain(DefaultClass.class);
		Assert.assertFalse(r);
	}
	
	@org.junit.Test
	public void versionClassTest(){
		Version version = ClassTools.getClassVersion("10_bin/org/capcaval/lafabrique/lang/_test/ClassToolsTest.class");
		System.out.println(version.getVersionString());
		Assert.assertTrue(Version.factory.newVersion("1.7").getLongValue() <= version.getLongValue());
	}
}
