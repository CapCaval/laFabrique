package org.capcaval.ccoutils.data;

public interface DataMapReadOnly <T, K>{
	T getObjectFromKey(K key);
	Data<T> getDataObjectFromKey(K key);
	public void subscribeToData(K key, DataEvent<T> dataObserver);
}
