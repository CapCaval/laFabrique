package org.capcaval.cccoutils.lang.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.ScheduledExecutorService;

public class ProxyTools {

	
	public static <T> T newThreadProxy(Class<T>proxyType, T instance, ScheduledExecutorService ses){
		// allocate the invocation handler
		ProxyThreadInvocationHandler<T> pih = new ProxyThreadInvocationHandler<T>(instance, ses);
		
		T proxy = (T) Proxy.newProxyInstance(ProxyInvocationHandler.class.getClassLoader(),
				new Class[]{proxyType}, 
				pih);
		
		return proxy;
	}
}
