package org.capcaval.cctools.converter.basicconverters;

import org.capcaval.cctools.converter.Converter;

public class StringToEnum implements Converter<String, Enum> {

	@Override
	public Enum convert(String inobj) {
		return null;
	}

	@Override
	public Class<String> getInputType() {
		return String;
	}

	@Override
	public Class<Enum> getOutputType() {
		return null;
	}


}
