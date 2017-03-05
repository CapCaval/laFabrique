package org.capcaval.lafabrique.lang.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.capcaval.lafabrique.lang.object.ObjectTools;

public class ProxyThreadInvocationHandler <T> extends ProxyInvocationHandler<T>{

	public enum ParamStrategy{keepParam, cloneParam};
	
	private ScheduledExecutorService ses;
	private ParamStrategy paramStrategy = ParamStrategy.keepParam;
	private ObjectTools objectTools = ObjectTools.factory.newInstance();

	public ProxyThreadInvocationHandler(T proxyImpl, ScheduledExecutorService ses) {
		this( proxyImpl, ses, ParamStrategy.keepParam);
	}
	
	public ProxyThreadInvocationHandler(T proxyImpl, ScheduledExecutorService ses, ParamStrategy paramStrategy) {
		super(proxyImpl);
		this.ses =ses;
		this.paramStrategy = paramStrategy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] argList) throws Throwable {
		
		if( this.paramStrategy == ParamStrategy.cloneParam){
			argList = this.objectTools.cloneObject(argList);
		}

		this.ses.schedule(new ProxyTask(this.proxyImpl, method, argList), 0, TimeUnit.NANOSECONDS);
		
		return null;
	}
}
