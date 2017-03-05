package org.capcaval.lafabrique.classgen.converters;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class IntegerToStringLiteralConverter extends ConverterAbstract<Integer, String> {

	@Override
	public String convert(Integer inobj) {
		return Integer.toString(inobj);
	}
}
