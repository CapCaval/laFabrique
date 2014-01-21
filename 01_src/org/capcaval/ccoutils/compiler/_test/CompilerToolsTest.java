package org.capcaval.ccoutils.compiler._test;

import java.io.IOException;

import org.capcaval.ccoutils.compiler.CompilerTools;
import org.capcaval.ccoutils.file.FileTools;
import org.junit.Assert;
import org.junit.Test;

public class CompilerToolsTest {

	@Test
	public void compilerSimpleTest() {
		
		try {
			Type type = CompilerTools.compile(Type.class, "01_src", "org/capcaval/ccoutils/compiler/_test/Type.java", "01_src");
			Assert.assertEquals("Hello, it is working!", type.test);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		} 
	}
	
	@Test
	public void compilerInterfaceTest(){
		try {
			MyInterface instance= CompilerTools.compile(MyInterface.class, "01_src", "org/capcaval/ccoutils/compiler/_test/MyClass.java", "01_src");
			Assert.assertEquals("Cool this class has been compiled!", instance.getValue());
			System.out.println("val : " + instance.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void compilerTest(){
		try {
			MyInterface instance= CompilerTools.compile(MyInterface.class, "01_src/org/capcaval/ccoutils/compiler/_test/MyClass.java");
			Assert.assertEquals("Cool this class has been compiled!", instance.getValue());
			System.out.println("val : " + instance.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
