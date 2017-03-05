package org.capcaval.lafabrique.factory._impl;

import java.util.concurrent.atomic.AtomicReference;

import org.capcaval.lafabrique.factory.GenericFactory;
import org.capcaval.lafabrique.lang.ClassTools;

public class GenericFactoryImpl<T> implements GenericFactory<T> {

	protected AtomicReference<Class<? extends T>> objImplTypeRef = new AtomicReference<>();
	//protected Class<? extends T> objImplType;

	public GenericFactoryImpl(Class<? extends T> objImplType) {
		// check that the implementation has a constructor without parameter
		if(ClassTools.isConstructorWithoutArgument(objImplType) == false){
			throw new RuntimeException("[laFabrique] ERROR :  The class " + objImplType + " does not have a constructor with no-parameter. The generic factory can not allocate such kind of class. Please add a default constructor. ");
		}
		
		// set the type and allocate the new type of object to be created
		this.objImplTypeRef.set(objImplType);
	}
	
	@Override
	public T newInstance() {
		T instance = null;
		try {
			instance = this.objImplTypeRef.get().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return instance;
	}
	
	@Override
	public void setObjectImplementationType(Class<? extends T> objImplType) {
		this.objImplTypeRef.set(objImplType);
		
	}

	@Override
	public Class<? extends T> getObjectImplementationType() {
		return this.objImplTypeRef.get();
	}
}
