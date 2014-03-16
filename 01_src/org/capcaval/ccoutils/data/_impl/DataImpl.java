package org.capcaval.ccoutils.data._impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.ccoutils.converter.Converter;
import org.capcaval.ccoutils.data.Data;
import org.capcaval.ccoutils.data.DataEvent;


public class DataImpl<T> implements Data<T> {

	T value;
	Map<Class<?>, Converter<?, T>> converterMap = new HashMap<>();
	List<DataEvent<T>> observerList = new ArrayList<>();

	public DataImpl(){
	}
	
	public DataImpl(T value){
		this.setValue(value);
	}
	
	@Override
	public void setValue(T value) {
		this.value = value;
		this.notifyDataUpdated(this.value);
	}

	public void notifyDataUpdated(T value) {
		for(DataEvent<T> observer : this.observerList){
			observer.notifyDataUpdated(value);
		}
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void feed(Object obj) {
		// get the object type
		Class<?> typeClass = obj.getClass();
		
		// find a converter
		@SuppressWarnings("rawtypes")
		Converter converter = this.converterMap.get(typeClass);
		
		if(converter==null){
			throw new RuntimeException("[ccOutils.Data] ERROR : can not find a converter for " + typeClass + " type to " + this.value.getClass() + " type.\n" +
					"Add the needed converter with the addDataConverter method." );
		}
			

		// convert it 
		@SuppressWarnings("unchecked")
		T conObj = (T)converter.convert(obj);
		
		// set the value
		this.setValue(conObj);
	}

	@Override
	public void addDataConverter(Converter<?, T> converter) {
		this.converterMap.put(converter.getOutputType(), converter);
	}

	@Override
	public void subscribeToData(DataEvent<T> dataObserver) {
		this.observerList.add(dataObserver);
		// notify the observer with the current value
		dataObserver.notifyDataUpdated(this.value);
	}
}
