package org.capcaval.lafabrique.lang._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.lang.ExceptionTools;

public class ExceptionToolsTest {
	@org.junit.Test
	public void ExceptionToStringTest(){
		try{
			throw new RuntimeException();
			
		}catch(Exception e){
			String str = ExceptionTools.stackTraceToString(e);
			Assert.assertTrue(str.contains("java.lang.RuntimeException"));
		}
	}
}
