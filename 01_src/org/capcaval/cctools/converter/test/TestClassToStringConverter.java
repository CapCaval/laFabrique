package org.capcaval.cctools.converter.test;

import org.capcaval.cctools.converter.ConverterAbstract;

public class TestClassToStringConverter extends ConverterAbstract<TestClass, String> {

	@Override
	public String convert(TestClass inobj) {
		String str = Integer.toString(inobj.intValue);
		str = str + ";" + Integer.toString(inobj.color.getRGB());
		return str;
	}

}
