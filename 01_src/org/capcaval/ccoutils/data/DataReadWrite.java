package org.capcaval.ccoutils.data;

public interface DataReadWrite<T> extends DataReadOnly<T> {
	public void setValue(T value);
	public void subscribeToData(DataEvent<T> dataObserver);
	
}
