package org.capcaval.ccoutils.commandline;

public class CommandParamDesc {
	public String name;
	public Class<?> type;
	public String description;

	public CommandParamDesc(String name, Class<?> type, String description){
		this.name = name;
		this.type = type;
		this.description = description;
	}
}
