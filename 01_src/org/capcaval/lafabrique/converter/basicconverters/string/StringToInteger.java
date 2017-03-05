package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;


public class StringToInteger extends ConverterAbstract<String, Integer> {
	@Override
	public Integer convert(String inobj) {
		return Integer.decode(inobj);
	}
}
