package org.capcaval.lafabrique.data;

public interface DataListEvent<T> {
	@SuppressWarnings("unchecked")
	void notifyObjectCreated(T... objectList);
	@SuppressWarnings("unchecked")
	void notifyObjectUpdated(T... objectList);
	@SuppressWarnings("unchecked")
	void notifyObjectDeleted(T... objectList);
}
