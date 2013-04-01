package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class StringToFloat extends ConverterAbstract<String, Float> {
	@Override
	public Float convert(String inobj) {
		return Float.parseFloat(inobj);
	}

}
