package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;

public class FloatToString extends ConverterAbstract<Float, String> {
	@Override
	public String convert(Float inobj) {
		return Float.toString(inobj);
	}

}
