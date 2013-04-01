package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class FloatToString extends ConverterAbstract<Float, String> {
	@Override
	public String convert(Float inobj) {
		return Float.toString(inobj);
	}

}
