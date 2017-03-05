package org.capcaval.lafabrique.lang.dynamiccaller;

import java.lang.reflect.Method;

public interface DynamicCallerInterface {

	public Object call(Method method, Object... argsArray) throws Exception;

}
