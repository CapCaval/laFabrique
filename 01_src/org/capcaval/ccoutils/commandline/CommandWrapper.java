package org.capcaval.ccoutils.commandline;

import java.lang.reflect.Method;

public class CommandWrapper {
	public String commandStr;
	public Object instance;
	public Method method;
	public boolean isContaingList;

	public CommandWrapper(String name, Method method, Object instance) {
		this.commandStr = name;
		this.instance = instance;
		this.method = method;
		// default value is false
		this.isContaingList = false;
	}
}
