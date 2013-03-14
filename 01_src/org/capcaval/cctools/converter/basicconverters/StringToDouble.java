package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;


public class StringToDouble extends ConverterAbstract<String, Double> {
	@Override
	public Double convert(String inobj) {
		return Double.parseDouble(inobj);
	}
}
