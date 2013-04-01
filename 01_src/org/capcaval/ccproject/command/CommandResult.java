package org.capcaval.ccproject.command;

public class CommandResult {
	protected boolean succeed;
	protected String message; 
	

	public CommandResult(boolean succeed, String message){
		this.succeed = false;
		this.message = message;
	}
	
	public CommandResult(String error, Throwable t){
		this.succeed = false;
	}

	public String getMessage() {
		return this.message;
	}

}
