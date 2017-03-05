package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;


public class IntegerToString extends ConverterAbstract<Integer, String> {
	@Override
	public String convert(Integer inobj) {
		return Integer.toString(inobj);
	}
}
