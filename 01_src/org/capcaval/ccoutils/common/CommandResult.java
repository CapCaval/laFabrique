package org.capcaval.ccoutils.common;

import org.capcaval.ccoutils.lang.StringMultiLine;

public class CommandResult {
	public static String applicationName = "ccOutils";
	
	public boolean isCommandFound;
	
	enum Type {ERROR, WARNING, INFO};
	
	protected Type type;
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
		this.type = Type.INFO;
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
	
	@Override
	public String toString(){
		return "[" + applicationName + "] " +  this.type + "  : " + this.message; 
	}
}
