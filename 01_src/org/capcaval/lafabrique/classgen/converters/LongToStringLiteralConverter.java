package org.capcaval.lafabrique.classgen.converters;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class LongToStringLiteralConverter extends ConverterAbstract<Long, String> {

	@Override
	public String convert(Long inobj) {
		return Long.toString(inobj) + "l";
	}
}
