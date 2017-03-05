package org.capcaval.lafabrique.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class ProxyInvocationHandler <T> implements InvocationHandler{

	protected T proxyImpl;
	
	public ProxyInvocationHandler(T proxyImpl){
		this.setProxyImpl(proxyImpl);
	}
	
	public void setProxyImpl(T proxyImpl){
		this.proxyImpl = proxyImpl;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] argList) throws Throwable {
		// do nothing with the proxy instance as we already get it 
		return method.invoke(this.proxyImpl, argList);
	}

}
