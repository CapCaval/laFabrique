package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;


public class StringToDouble extends ConverterAbstract<String, Double> {
	@Override
	public Double convert(String inobj) {
		return Double.parseDouble(inobj);
	}
}
