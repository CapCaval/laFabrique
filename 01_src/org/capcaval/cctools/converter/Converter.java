package org.capcaval.cctools.converter;

public interface Converter <I,O>{
	public O convert(I inobj);
	
	Class<I> getInputType();
	Class<O> getOutputType();
}
