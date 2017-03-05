package org.capcaval.lafabrique.lang._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.lang.JVM;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.lang.Version;
import org.junit.Test;

public class SystemToolsTest {
	
	public class Caller{
		public Class<?> getCaller(){
			return SystemTools.getCallerType();
		}
	}

	@Test
	public void test() {
		Caller caller = new Caller();
		// the caller shall be the test
		Assert.assertEquals( Caller.class, caller.getCaller());
	}
	
	@Test
	public void getCurrentFullMethodNameTest(){
		String methodName = SystemTools.getCurrentFullMethodName();
		// check the name
		Assert.assertEquals(this.getClass().getName() + ".getCurrentFullMethodNameTest()", methodName);
	}
	
	@Test
	public void getJavaVersionTest(){
		System.out.println(System.getProperty("java.home"));
		
		Version version = SystemTools.getCurrentJavaVersion();
		System.out.println(version);
		Assert.assertNotNull(version);
	}	

	@Test
	public void getJDKInstallationPathTest(){
		System.out.println(System.getProperty("java.home"));
		System.out.println(System.getenv("PATH"));
		
		//System.setProperty("java.home", "D:\\prg\\Java\\jdk1.7.0_51");
		
		JVM info = SystemTools.getJDKInstallationInfo();
		System.out.println("JDK : " + info.getPath().toString());
		Assert.assertNotNull(info);
		
	}	
	
	@Test
	public void executeClassInAnotherJVMTest(){
		String out = SystemTools.executeClassInAnotherJVM(TestMain.class, "1");
		System.out.println(out);
		Assert.assertEquals("Main application.1", out);
	}
	
	@Test
	public void getUserNameTest(){
		String name = SystemTools.getUserName();
		System.out.println(name);
		Assert.assertNotNull(name);
		Assert.assertTrue(name.length()>0);
	}
}
