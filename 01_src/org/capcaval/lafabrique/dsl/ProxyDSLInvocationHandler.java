package org.capcaval.lafabrique.dsl;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.lafabrique.lang.object.ObjectTools;
import org.capcaval.lafabrique.lang.proxy.ProxyInvocationHandler;

public class ProxyDSLInvocationHandler <T> extends ProxyInvocationHandler<T>{

	GenericDSLObject genericDSLObject = null; 
	
	public ProxyDSLInvocationHandler(T proxyImpl) {
		super(proxyImpl);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] argList) throws Throwable {


		
		return null;
	}
}
