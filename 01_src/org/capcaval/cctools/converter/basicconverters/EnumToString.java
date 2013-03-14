package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.ConverterAbstract;

public class EnumToString extends ConverterAbstract<Enum, String> {
	@Override
	public String convert(Enum inobj) {
		return inobj.toString();
	}

}
