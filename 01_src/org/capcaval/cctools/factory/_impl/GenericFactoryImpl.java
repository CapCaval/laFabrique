package org.capcaval.cctools.factory._impl;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.capcaval.cctools.factory.GenericFactory;
import org.capcaval.cctools.lang.ClassTools;
import org.capcaval.cctools.recycling.ObjectPool;

public class GenericFactoryImpl<T> implements GenericFactory<T> {

	protected Class<? extends T> objImplType;
	protected ObjectPool<T> objectPool;

	public GenericFactoryImpl(Class<? extends T> objImplType) {
		// set the type and allocate the new type of object to be created
		this.objImplType = objImplType;
	}
	
	@Override
	public T newInstance() {
		T instance = null;
		if(this.objectPool == null){
			// without a pool do it the normal way
			instance = this.newStandardInstance();
		}else{
			// if the pool is set use it
			instance = this.objectPool.getNewFreeInstance();
		}
		
		return instance;
	}
	
	public T newInstance(Object... objList) {
		if(this.objectPool != null){
			new RuntimeException("[ccTools]Error:Calling newInstance method with parameter is not available when a pool is set.");
		}
		
		Class<?>[] cmdParamTypeList = ClassTools.getAllType(objList);
		
		T instance = null;
		try {
			Constructor<?>[] ctorlist = this.objImplType.getConstructors();
			for(Constructor<?> ctor : ctorlist){
				// get the type list attributes
				Class<?>[] ctorParamTypeList = ctor.getParameterTypes();
				if((cmdParamTypeList.length == ctorParamTypeList.length)&&
						(Arrays.equals(cmdParamTypeList, ctorParamTypeList))){
					instance= (T)ctor.newInstance(objList);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}

	protected T newStandardInstance(){
		T instance = null;
		try {
			instance = this.objImplType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	
	@Override
	public void setObjectImplementationType(Class<? extends T> objImplType) {
		this.objImplType = objImplType;
		
	}

	@Override
	public Class<? extends T> getObjectImplementationType() {
		return this.objImplType;
	}

	@Override
	public void releaseInstance(T obj) {
		this.objectPool.releaseInstance(obj);
	}

	@Override
	public void setObjectPoolSize(int nbOfInstance) {
		this.objectPool = new ObjectPool<T>((Class<T>)this.objImplType, nbOfInstance);
		
	}
}
