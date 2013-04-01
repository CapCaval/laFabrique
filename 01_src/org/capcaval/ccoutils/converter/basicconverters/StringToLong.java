package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class StringToLong extends ConverterAbstract<String, Long> {
	@Override
	public Long convert(String inobj) {
		return Long.parseLong(inobj);
	}

}
