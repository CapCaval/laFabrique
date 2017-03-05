package org.capcaval.lafabrique.converter._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToInteger;
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
