package org.capcaval.ermine.jfx._test;

import junit.framework.Assert;

import org.junit.Test;

public class JfxApplicationTest {

	@Test
	public void threadTest(){
		JfxApp.main(new String[]{"test"});
		Assert.assertEquals(JfxApp.testThread.getName(), "toto");
	}
}
