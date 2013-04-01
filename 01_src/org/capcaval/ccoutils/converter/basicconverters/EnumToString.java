package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class EnumToString extends ConverterAbstract<Enum<?>, String> {
	@Override
	public String convert(Enum inobj) {
		Class<?> c = inobj.getDeclaringClass();
		
		return c.getSimpleName()+"."+inobj.toString();
	}

}
