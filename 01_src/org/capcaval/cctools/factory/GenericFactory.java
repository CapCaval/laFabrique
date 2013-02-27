package org.capcaval.cctools.factory;

import org.capcaval.cctools.factory._impl.GenericFactoryImpl;

public interface GenericFactory <T>{
	static public GenericFactory<GenericFactory<?>> factory = new GenericFactoryImpl(GenericFactoryImpl.class);
	
	T newInstance();
	T newInstance(Object... objList);

	void setObjectPoolSize(int nbOfInstance);
	void releaseInstance(T obj);
	
	void setObjectImplementationType(Class<? extends T> type);
	Class<? extends T> getObjectImplementationType();
}
