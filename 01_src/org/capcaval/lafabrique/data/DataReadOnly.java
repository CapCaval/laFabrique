package org.capcaval.lafabrique.data;

public interface DataReadOnly <T>{
	public T getValue();
	public void subscribeToData(DataEvent<T> dataObserver);
}
