package org.capcaval.lafabrique.lang._test;

import org.capcaval.lafabrique.lang.NumberTools;
import org.junit.Assert;
import org.junit.Test;

public class NumberToolsTest {

	@Test
	public void oddEvenTest(){
		Assert.assertTrue(NumberTools.isOdd(1));
		Assert.assertFalse(NumberTools.isEven(1));
		
		Assert.assertFalse(NumberTools.isOdd(10));
		Assert.assertTrue(NumberTools.isEven(10));

	}
	
}
