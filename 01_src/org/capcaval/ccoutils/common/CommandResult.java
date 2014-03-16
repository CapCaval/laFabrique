package org.capcaval.ccoutils.common;

import org.capcaval.ccoutils.lang.StringMultiLine;

public class CommandResult {
	public static String applicationName = "ccOutils";
	
	public boolean isCommandFound;
	
	public enum Type {ERROR, WARNING, INFO, NONE};
	
	protected Type type = Type.NONE;
	protected StringMultiLine message = new StringMultiLine(); 
	protected Throwable throwable;	

	public CommandResult() {
		this.message.addLine("");
		this.isCommandFound = false;
	}
	
	public CommandResult(String returnMessage, boolean isCommandFound){
		this.message.addLine(returnMessage);
		this.isCommandFound = isCommandFound;
	}
	
	public CommandResult(String message) {
		this.init(Type.INFO, message);
	}
	
	public CommandResult(Type type, String message) {
		this.init(type, message);
	}

	public void init(Type type, String message) {
		this.type = type;
		this.message.addLine(message);
		this.isCommandFound = true;
	}

	
	public String getReturnMessage(){
		return this.message.toString();
	}
	
	public boolean isCommandFound(){
		return this.isCommandFound;
	}
	
	public void addMessage(String message){
		this.message.addLine(message);
	}
	
	public Type getType(){
		return this.type;
	}
	
	@Override
	public String toString(){
		String str = null;
		// if type is none just take the string as it is
		if(this.type == Type.NONE){
			str = this.message.toString();
		}else{ // otherwise format the message with the type and application name
			str = "[" + applicationName + "] " +  this.type + "  : " + this.message;
		}
		return str;
	}
}
