package org.capcaval.ccoutils.factory;

import org.capcaval.ccoutils.factory._impl.GenericFactoryImpl;

public interface GenericFactory <T>{
	static public GenericFactory<GenericFactory<?>> factory = new GenericFactoryImpl(GenericFactoryImpl.class);
	
	T newInstance();
	T newInstance(Object... objList);

	void setObjectPoolSize(int nbOfInstance);
	void releaseInstance(T obj);
	
	void setObjectImplementationType(Class<? extends T> type);
	Class<? extends T> getObjectImplementationType();
}
