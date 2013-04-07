package org.capcaval.ccoutils.data;

public interface DataEvent<T> {
	public void notifyDataUpdated(T data);
}
