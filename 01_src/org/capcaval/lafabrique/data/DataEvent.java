package org.capcaval.lafabrique.data;

public interface DataEvent<T> {
	public void notifyDataUpdated(T data);
}
