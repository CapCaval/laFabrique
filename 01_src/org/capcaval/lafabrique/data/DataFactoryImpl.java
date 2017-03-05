package org.capcaval.lafabrique.data;

import java.util.ArrayList;

import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.basicconverters.string.DoubleToString;
import org.capcaval.lafabrique.converter.basicconverters.string.EnumToString;
import org.capcaval.lafabrique.converter.basicconverters.string.FloatToString;
import org.capcaval.lafabrique.converter.basicconverters.string.IntegerToString;
import org.capcaval.lafabrique.converter.basicconverters.string.LongToString;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToDouble;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToEnum;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToFloat;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToInteger;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToLong;
import org.capcaval.lafabrique.converter.basicconverters.string.StringToString;
import org.capcaval.lafabrique.data.Data.DataFactory;
import org.capcaval.lafabrique.data._impl.DataImpl;

public class DataFactoryImpl implements DataFactory {

	private ArrayList<Converter<?,?>> converterList;

	DataFactoryImpl(){
		// allocate some default converter
		this.converterList = new ArrayList<Converter<?,?>>();
		this.converterList.add(new DoubleToString());
		this.converterList.add(new EnumToString());
		this.converterList.add(new FloatToString());
		this.converterList.add(new IntegerToString());
		this.converterList.add(new LongToString());
		this.converterList.add(new StringToDouble());
		this.converterList.add(new StringToEnum());
		this.converterList.add(new StringToFloat());
		this.converterList.add(new StringToInteger());
		this.converterList.add(new StringToLong());
		this.converterList.add(new StringToString());
	}
	
	@Override
	public <T> Data<T> newData() {
		Data<T> dataInstance = new DataImpl<T>();
		// fill the default transformer
		for(Converter<?,?> converter : this.converterList){
			if(converter.getOutputType() == dataInstance.getValue().getClass()){
				dataInstance.addDataConverter((Converter<?,T>)converter);}
		}
		
		return dataInstance;
	}

	@Override
	public <T> Data<T> newData(T data) {
		Data<T> dataInstance = new DataImpl<T>(data);
		// fill the default transformer
		for(Converter<?,?> converter : this.converterList){
			if(converter.getOutputType() == data.getClass()){
				dataInstance.addDataConverter((Converter<?,T>)converter);}
		}
		return dataInstance;
	}

	@Override
	public <T> DataReadOnly<T> newReadOnlyData(T data) {
		return new DataImpl<T>(data);
	}

	
	
}
