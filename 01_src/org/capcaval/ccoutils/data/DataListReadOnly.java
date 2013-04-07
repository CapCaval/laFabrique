package org.capcaval.ccoutils.data;

public interface DataListReadOnly <T>{
	T getObject(int index);
	Data<T> getDataObject(int index);
	T[] getAllObject();
	Data<T>[] getAllDataObject();
	public void subscribeToData(DataListEvent<T> dataObserver);
}
