package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class DoubleToString extends ConverterAbstract<Double, String> {
	@Override
	public String convert(Double inobj) {
		return Double.toString(inobj);
	}

}
