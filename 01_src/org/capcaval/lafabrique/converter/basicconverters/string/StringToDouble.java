package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;


public class StringToDouble extends ConverterAbstract<String, Double> {
	@Override
	public Double convert(String inobj) {
		return Double.parseDouble(inobj);
	}
}
