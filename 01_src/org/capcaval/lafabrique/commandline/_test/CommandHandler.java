package org.capcaval.lafabrique.commandline._test;

import java.lang.reflect.Method;

public interface CommandHandler {
	public Object[] handle(Object proxy, Method method, Object[] paramArray);
}
