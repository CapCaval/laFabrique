package org.capcaval.cctools.converter.test;

import java.awt.Color;

import junit.framework.Assert;

import org.capcaval.cctools.converter.Converter;
import org.capcaval.cctools.converter.ConverterManager;
import org.junit.Test;

public class ConverterManagerTest {
	@Test
	public void addingConverterTest(){
		ConverterManager cm = new ConverterManager();
		
		TestClassToStringConverter converter = new TestClassToStringConverter();
		
		cm.addConverter(converter);
		
		Converter<TestClass, String> c2 = cm.getConverter(TestClass.class, String.class);
		TestClass tc = new TestClass();
		tc.color = Color.BLUE;
		tc.intValue = 5;
		String teststr = c2.convert(tc);
		
		Assert.assertEquals("5;-16776961", teststr);
		Assert.assertEquals(converter, c2);
	}
	@Test
	public void replaceConverterTest(){
		ConverterManager cm = new ConverterManager();
		
		TestClassToStringConverter converter = new TestClassToStringConverter();
		TestClassToStringConverter converter2 = new TestClassToStringConverter();
		
		cm.addConverter(converter);
		cm.addConverter(converter2);
		
		Converter<TestClass, String> cout = cm.getConverter(TestClass.class, String.class);
		
		Assert.assertEquals(converter2, cout);
		
	}
}
