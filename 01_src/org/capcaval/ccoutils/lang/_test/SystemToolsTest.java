package org.capcaval.ccoutils.lang._test;

import junit.framework.Assert;

import org.capcaval.ccoutils.lang.SystemTools;
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

}
