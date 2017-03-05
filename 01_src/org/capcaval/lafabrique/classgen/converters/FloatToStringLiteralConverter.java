package org.capcaval.lafabrique.classgen.converters;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class FloatToStringLiteralConverter extends ConverterAbstract<Float, String> {

	@Override
	public String convert(Float inobj) {
		return Float.toString(inobj) + "f";
	}
}
