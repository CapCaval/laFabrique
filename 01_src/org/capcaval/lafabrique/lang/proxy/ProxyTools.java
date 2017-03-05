package org.capcaval.lafabrique.lang.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.lafabrique.lang.proxy.ProxyThreadInvocationHandler.ParamStrategy;

public class ProxyTools {

	public static <T> T newThreadProxy(Class<T>proxyType, T instance, ScheduledExecutorService ses, ParamStrategy strategy){
		// allocate the invocation handler
		ProxyThreadInvocationHandler<T> pih = new ProxyThreadInvocationHandler<T>(instance, ses, strategy);
		
		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(ProxyInvocationHandler.class.getClassLoader(),
				new Class[]{proxyType}, 
				pih);
		
		return proxy;
	}
	
	public static <T> T newThreadProxy(Class<T>proxyType, T instance, ScheduledExecutorService ses){
		// allocate the invocation handler
		ProxyThreadInvocationHandler<T> pih = new ProxyThreadInvocationHandler<T>(instance, ses);
		
		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(ProxyInvocationHandler.class.getClassLoader(),
				new Class[]{proxyType}, 
				pih);
		
		return proxy;
	}

}
