package org.capcaval.c3.componentmanager.tools;

import java.lang.reflect.Method;

public class Task {
	protected Method method;
	protected Class<?> ParentType;
	protected Object instance;
	
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Class<?> getParentType() {
		return ParentType;
	}
	public void setParentType(Class<?> parentType) {
		ParentType = parentType;
	}
	public Object getInstance() {
		return instance;
	}
	public void setInstance(Object instance) {
		this.instance = instance;
	}
}
