package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;

public class DoubleToString extends ConverterAbstract<Double, String> {
	@Override
	public String convert(Double inobj) {
		return Double.toString(inobj);
	}

}
