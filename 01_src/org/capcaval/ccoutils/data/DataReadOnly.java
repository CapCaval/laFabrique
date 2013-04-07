package org.capcaval.ccoutils.data;

public interface DataReadOnly <T>{
	public T getValue();
	public void subscribeToData(DataEvent<T> dataObserver);
}
