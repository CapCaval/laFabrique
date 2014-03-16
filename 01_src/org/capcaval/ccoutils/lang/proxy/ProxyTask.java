package org.capcaval.cccoutils.lang.proxy;

import java.lang.reflect.Method;

public class ProxyTask implements Runnable{
	protected Object[] argList;
	protected Object instance;
	protected Method method;

	public ProxyTask(Object instance, Method method, Object[] argList){
		this.argList = argList;
		this.instance = instance;
		this.method = method;
	}
	
	@Override
	public void run() {
		try {
			this.method.invoke(this.instance, this.argList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
