package org.capcaval.lafabrique.factory._impl;

import org.capcaval.lafabrique.factory.GenericFactoryWithPool;
import org.capcaval.lafabrique.recycling.ObjectPool;

public class GenericFactoryWithPoolImpl<T> extends GenericFactoryImpl<T> implements GenericFactoryWithPool<T> {

	protected ObjectPool<T> objectPool;

	public GenericFactoryWithPoolImpl(Class<? extends T> objImplType) {
		super(objImplType);
	}
	
	@Override
	public T newInstance() {
		return this.objectPool.getNewFreeInstance();
	}
	
	public T newInstance(Object... objList) {
		throw(new RuntimeException("[ccOutils] Error : Calling newInstance method with parameters is not available when a pool is set."));
	}
	
	@Override
	public void releaseInstance(T obj) {
		this.objectPool.releaseInstance(obj);
	}

	@Override
	public void setObjectPoolSize(int nbOfInstance) {
		this.objectPool = new ObjectPool<T>((Class<T>)this.objImplTypeRef.get(), nbOfInstance);
		
	}
}
