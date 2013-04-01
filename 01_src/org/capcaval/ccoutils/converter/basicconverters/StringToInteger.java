package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;


public class StringToInteger extends ConverterAbstract<String, Integer> {
	@Override
	public Integer convert(String inobj) {
		return Integer.decode(inobj);
	}
}
