package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class LongToString extends ConverterAbstract<Long, String> {
	@Override
	public String convert(Long inobj) {
		return Long.toString(inobj);
	}

}
