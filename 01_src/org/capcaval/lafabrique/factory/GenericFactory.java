package org.capcaval.lafabrique.factory;

public interface GenericFactory <T>{
	
	T newInstance();
	
	void setObjectImplementationType(Class<? extends T> type);
	Class<? extends T> getObjectImplementationType();
}
