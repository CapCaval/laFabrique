package org.capcaval.lafabrique.converter;

import java.util.IdentityHashMap;
import java.util.Map;

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
import org.capcaval.lafabrique.lang.object.PrimitiveToObjectMap;

public class ConverterManager {

	protected Map<Class<?>, Map<Class<?>, Converter<?,?>>> map = new IdentityHashMap<>();
	protected PrimitiveToObjectMap primitiveToObjectMap = new PrimitiveToObjectMap();
	
	public ConverterManager(){
		this.addDefaultConverter();
	}

	public ConverterManager(Converter<?,?>... converterArray){
		
		for(Converter<?,?>converter : converterArray){
			this.addConverter(converter);
		}
	}

	public void addDefaultConverter(){
		this.addConverter(new StringToInteger());
		this.addConverter(new StringToDouble());
		this.addConverter(new IntegerToString());
		this.addConverter(new DoubleToString());
		this.addConverter(new EnumToString());
		this.addConverter(new StringToEnum());
		this.addConverter(new FloatToString());
		this.addConverter(new StringToFloat());
		this.addConverter(new LongToString());
		this.addConverter(new StringToLong());
		this.addConverter(new StringToString());
	}
	
	public void addConverter( Converter<?,?> converter){
		// get the input type
		Class<?> inputType = converter.getInputType();
		// add the new converter
		this.addConverter(converter, inputType);
		
		// check out if the inputType has a corresponding primitive
		Class<?> type = this.primitiveToObjectMap.getPrimTypeFromObjectType(inputType);
		
		if(type != null){
			// register also the primitive with the same converter
			this.addConverter(converter, type);
		}
	}
	
	public void addConverter( Converter<?,?> converter, Class<?> inputType){
		Map<Class<?>, Converter<?,?>> m = this.map.get(inputType);
		
		// allocate a new one if none
		if(m==null){
			// allocate a new HashMap
			m = new IdentityHashMap<Class<?>, Converter<?,?>>();
			//add it
			this.map.put(inputType, m);
		}
		// add the out type
		m.put(converter.getOutputType(), converter);
	}

	public void addConverter(Converter<?,?>[] converterList) {
		for(Converter<?,?> converter : converterList){
			this.addConverter(converter);
		}
	}

	public <I,O> Converter<I,O> getConverter(Class<I> fromType, Class<O> outType){
		Converter<I,O> converter = null;
		
		Map<Class<?>, Converter<?,?>> m = this.map.get(fromType);
		// find the inType try to find the out one, if none return null
		if(m != null){
			converter = (Converter<I,O>)m.get(outType);
		}
		
		return converter;
	}

	@SuppressWarnings("unchecked")
	public <I,O> Converter<I,Object> getGenericInConverter(Class<I> fromType, Class<O> outType){
		Converter<I,Object> converter = null;
		
		Map<Class<?>, Converter<?,?>> m = this.map.get(fromType);
		// find the inType try to find the out one, if none return null
		if(m != null){
			converter = (Converter<I,Object>)m.get(outType);
		}
		
		return converter;
	}

	@SuppressWarnings("unchecked")
	public <I,O> Converter<Object,O> getGenericOutConverter(Class<I> fromType, Class<O> outType){
		Converter<Object,O> converter = null;
		
		Map<Class<?>, Converter<?,?>> m = this.map.get(fromType);
		// find the inType try to find the out one, if none return null
		if(m != null){
			converter = (Converter<Object,O>)m.get(outType);
		}
		
		return converter;
	}	
}
