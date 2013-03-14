package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;


public class IntegerToString extends ConverterAbstract<Integer, String> {
	@Override
	public String convert(Integer inobj) {
		return Integer.toString(inobj);
	}
}
