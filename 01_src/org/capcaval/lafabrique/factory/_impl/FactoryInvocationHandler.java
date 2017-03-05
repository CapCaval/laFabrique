package org.capcaval.lafabrique.factory._impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FactoryInvocationHandler<T> implements InvocationHandler {

	T factoryImpl;
	
	public FactoryInvocationHandler(T factoryImpl){
		this.factoryImpl = factoryImpl;
	}

	public void SetFactoryImpl(T factoryImpl){
		this.factoryImpl = factoryImpl;
	}

	
	@Override
	public Object invoke(Object proxy, Method method, Object[] argList)
			throws Throwable {
		return method.invoke(factoryImpl, argList);
	}

	public Class<?> getFactoryImplType() {
		return this.factoryImpl.getClass();
	}

}
