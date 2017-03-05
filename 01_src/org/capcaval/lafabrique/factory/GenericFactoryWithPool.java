package org.capcaval.lafabrique.factory;


public interface GenericFactoryWithPool <T> extends GenericFactory<T>{
	void setObjectPoolSize(int nbOfInstance);
	void releaseInstance(T obj);
}
