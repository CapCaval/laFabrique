package org.capcaval.lafabrique.common;

import org.capcaval.lafabrique.lang.StringMultiLine;

public class CopyOfCommandResult {
	public static String applicationName = "ccOutils";
	
	public boolean isCommandFound;
	
	public enum Type {ERROR, WARNING, INFO, NONE};
	public enum ErrorType {COMMAND_NOT_FOUND, WRONG_PARAMETER, DOMAIN};
	
	protected Type type = Type.NONE;
	protected StringMultiLine message = new StringMultiLine(); 
	protected Throwable throwable;	
	protected String stringValue;

	public CopyOfCommandResult() {
		this.isCommandFound = false;
	}
	
	public CopyOfCommandResult(String returnMessage, boolean isCommandFound){
		if(returnMessage != null){
			this.message.addLine(returnMessage);
			this.stringValue = returnMessage;
			}
		this.isCommandFound = isCommandFound;
	}
	
	public CopyOfCommandResult(String message) {
		this.init(Type.INFO, message);
	}
	
	public CopyOfCommandResult(Type type, String message) {
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
	
	public String getStringValue(){
		return this.stringValue;
	}

	public void setStringValue(String stringValue){
		this.stringValue = stringValue;
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
