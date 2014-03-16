package org.capcaval.cccoutils.lang.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProxyThreadInvocationHandler <T> extends ProxyInvocationHandler<T>{

	private ScheduledExecutorService ses;

	public ProxyThreadInvocationHandler(T proxyImpl, ScheduledExecutorService ses) {
		super(proxyImpl);
		this.ses =ses;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] argList) throws Throwable {

		this.ses.schedule(new ProxyTask(this.proxyImpl, method, argList), 0, TimeUnit.NANOSECONDS);
		
		return null;
	}
}
