package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;


public class IntegerToString extends ConverterAbstract<Integer, String> {
	@Override
	public String convert(Integer inobj) {
		return Integer.toString(inobj);
	}
}
