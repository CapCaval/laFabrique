package org.capcaval.ccoutils.lang._test;

import junit.framework.Assert;

import org.capcaval.ccoutils.lang.Jdk;
import org.capcaval.ccoutils.lang.JdkPathInfo;
import org.capcaval.ccoutils.lang.SystemTools;
import org.capcaval.ccoutils.lang.Version;
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
		Assert.assertEquals( this.getClass(), caller.getCaller());
	}
	
	@Test
	public void getCurrentFullMethodNameTest(){
		String methodName = SystemTools.getCurrentFullMethodName();
		// check the name
		Assert.assertEquals(this.getClass().getName() + ".getCurrentFullMethodName()", methodName);
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
		
		JdkPathInfo info = SystemTools.getJDKInstallationInfo();
		System.out.println("JDK : " + info.path.toString());
		Assert.assertNotNull(info);
		
	}	
	
}
