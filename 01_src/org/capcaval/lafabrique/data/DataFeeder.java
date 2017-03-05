package org.capcaval.lafabrique.data;

import org.capcaval.lafabrique.converter.Converter;


public interface DataFeeder <T>{
	public void feed(Object obj);
	public void addDataConverter(Converter<?, T> converter);
}
