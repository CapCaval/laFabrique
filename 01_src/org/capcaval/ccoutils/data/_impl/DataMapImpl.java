package org.capcaval.ccoutils.data._impl;

import java.util.HashMap;
import java.util.Map;

import org.capcaval.ccoutils.data.Data;
import org.capcaval.ccoutils.data.DataEvent;
import org.capcaval.ccoutils.data.DataMap;

public class DataMapImpl<T, K> extends DataListImpl<T> implements DataMap<T, K> {
	Map<K, Data<T>> dataMap = new HashMap<>();
	
	@Override
	public void addObjectFromKey(K key, T object) {
		Data<T>data = new DataImpl<T>();
		data.setValue(object);
		this.dataMap.put(key, data);
	}

	@Override
	public void updateObjectFromKey(K key, T object) {
		Data<T> data = this.dataMap.get(key);
		data.setValue(object);
	}

	@Override
	public void deleteObjectFromKey(K key) {
		this.dataMap.remove(key);
	}


	@Override
	public T getObjectFromKey(K key) {
		return this.dataMap.get(key).getValue();
	}

	@Override
	public Data<T> getDataObjectFromKey(K key) {
		return this.dataMap.get(key);
	}

	@Override
	public void subscribeToData(K key, DataEvent<T> dataObserver) {
		this.dataMap.get(key).subscribeToData(dataObserver);
	}
}
