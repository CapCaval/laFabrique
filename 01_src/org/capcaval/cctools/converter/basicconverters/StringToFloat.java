package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;

public class StringToFloat extends ConverterAbstract<String, Float> {
	@Override
	public Float convert(String inobj) {
		return Float.parseFloat(inobj);
	}

}
