package org.capcaval.ccoutils.lafabrique.command;


public class CommandResult {
	
	public static String applicationName = "ccOutils";
	
	protected boolean succeed;
	protected String message; 
	

	public CommandResult(boolean succeed, String message){
		this.succeed = false;
		this.message = message;
	}
	
	public CommandResult(String error, Throwable t){
		this.succeed = false;
		this.message = t.getMessage();
	}

	public String getMessage() {
		return this.message;
	}
	
	@Override
	public String toString(){
		return "[" + applicationName + "] : " + this.message; 
	}

}
