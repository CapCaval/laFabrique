package org.capcaval.lafabrique.classgen.converters;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class StringToStringLiteralConverter extends ConverterAbstract<String, String> {

	@Override
	public String convert(String inobj) {
		return "\""+ inobj +"\"";
	}
}
