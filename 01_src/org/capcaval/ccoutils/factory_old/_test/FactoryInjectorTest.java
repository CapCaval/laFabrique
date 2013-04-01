package org.capcaval.ccoutils.factory_old._test;

import junit.framework.Assert;

import org.capcaval.ccoutils.factory_old.FactoryTools;

public class FactoryInjectorTest {

	@org.junit.Test
	public void FactoryInjectorTest() throws Exception{
		String result = StringFactory.factory.newString("Hello");
		Assert.assertEquals("Hello", result);
		
		FactoryTools.injectNewFactoryImplmentation(StringFactory.class, new StringFactory(){
			@Override
			public String newString(String str) {
				return str.toUpperCase();
			}});
		
		result = StringFactory.factory.newString("Hello");
		Assert.assertEquals("HELLO", result);

		
	}
	
}
