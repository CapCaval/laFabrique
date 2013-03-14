package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;


public class StringToInteger extends ConverterAbstract<String, Integer> {
	@Override
	public Integer convert(String inobj) {
		return Integer.decode(inobj);
	}
}
