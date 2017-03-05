package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class DoubleToString extends ConverterAbstract<Double, String> {
	@Override
	public String convert(Double inobj) {
		return Double.toString(inobj);
	}

}
