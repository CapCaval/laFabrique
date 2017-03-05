package org.capcaval.lafabrique.lang.dynamiccaller.aspects;

import java.lang.reflect.Method;

import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerAspect;
import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerInterface;
import org.capcaval.lafabrique.lang.object.ObjectTools;

public class CloneParametersAspect extends DynamicCallerAspect{
	protected Method method;
	protected Object[] argsArray;
	
	public CloneParametersAspect(DynamicCallerInterface originalInstance) {
		super(originalInstance);
	}

	@Override
	public Object call(Method method, Object... argsArray) throws Exception {
		// clone parameters
		ObjectTools objectTools =  ObjectTools.factory.newInstance();
		Object[] clonedParams = objectTools.cloneObject(argsArray);
		
		// no return value for async call
		return this.originalInstance.call(method, clonedParams);
	}
}
