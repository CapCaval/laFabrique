package org.capcaval.cctools.converter.test;

import junit.framework.Assert;

import org.capcaval.cctools.converter.ConverterManager;
import org.capcaval.cctools.converter.Converter;
import org.capcaval.cctools.converter.basicconverters.StringToInteger;
import org.junit.Test;

public class ConverterTest {

	@Test
	public void ConverterStringToIntegerTest(){
		Converter<String, Integer> c = new StringToInteger();
		Integer result = c.convert("5");
		
		Assert.assertEquals(Integer.valueOf(5), result);
	}

	@Test
	public void ConverterStringToIntegerInAndOutTypeTest(){
		Converter<String, Integer> c = new StringToInteger();
		
		Assert.assertEquals(String.class, c.getInputType());
		Assert.assertEquals(Integer.class, c.getOutputType());
	}

	
}
