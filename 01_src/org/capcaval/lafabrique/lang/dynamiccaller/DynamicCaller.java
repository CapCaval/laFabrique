package org.capcaval.lafabrique.lang.dynamiccaller;

import java.lang.reflect.Method;

public class DynamicCaller implements DynamicCallerInterface{
	protected Object instance;
	
	public DynamicCaller(Object instance){
		this.instance = instance;
	}

	@Override
	public Object call(Method method, Object... argsArray) throws Exception{
		return method.invoke(this.instance, argsArray);
	}
}
