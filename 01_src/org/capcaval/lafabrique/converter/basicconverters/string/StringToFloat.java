package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class StringToFloat extends ConverterAbstract<String, Float> {
	@Override
	public Float convert(String inobj) {
		return Float.parseFloat(inobj);
	}

}
