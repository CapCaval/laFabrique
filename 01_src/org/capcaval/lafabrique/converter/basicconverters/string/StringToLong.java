package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class StringToLong extends ConverterAbstract<String, Long> {
	@Override
	public Long convert(String inobj) {
		return Long.parseLong(inobj);
	}

}
