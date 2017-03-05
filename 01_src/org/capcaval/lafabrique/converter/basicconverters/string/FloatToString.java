package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class FloatToString extends ConverterAbstract<Float, String> {
	@Override
	public String convert(Float inobj) {
		return Float.toString(inobj);
	}

}
