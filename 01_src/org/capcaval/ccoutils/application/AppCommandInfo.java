package org.capcaval.ccoutils.application;

import java.lang.reflect.Method;

public class AppCommandInfo {
	protected Method method;
	protected Object instance;

	public Method getMethod() {
		return method;
	}
	public Object getInstance() {
		return instance;
	}
	public AppCommandInfo(Method method, Object instance) {
		this.method = method;
		this.instance = instance;
	}
}
