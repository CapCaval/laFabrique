package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;

public class StringToLong extends ConverterAbstract<String, Long> {
	@Override
	public Long convert(String inobj) {
		return Long.parseLong(inobj);
	}

}
