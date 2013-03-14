package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;

public class LongToString extends ConverterAbstract<Long, String> {
	@Override
	public String convert(Long inobj) {
		return Long.toString(inobj);
	}

}
