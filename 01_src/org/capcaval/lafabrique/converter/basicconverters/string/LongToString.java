package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class LongToString extends ConverterAbstract<Long, String> {
	@Override
	public String convert(Long inobj) {
		return Long.toString(inobj);
	}

}
