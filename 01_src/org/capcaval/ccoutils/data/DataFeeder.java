package org.capcaval.ccoutils.data;

import org.capcaval.ccoutils.converter.Converter;


public interface DataFeeder <T>{
	public void feed(Object obj);
	public void addDataConverter(Converter<?, T> converter);
}
