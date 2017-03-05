package org.capcaval.lafabrique.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.capcaval.lafabrique.lang.ExceptionTools;
import org.capcaval.lafabrique.lang.StringMultiLine;

public class CommandResult {
	public static AtomicReference<String>applicationName = new AtomicReference<>("ccOutils");
	
	public enum Type {NONE, INFO, WARNING, ERROR, COMMAND_NOT_FOUND};
	
	protected Type type;
	
	protected List<ResultItem> list = new ArrayList<>();
	
	protected Exception exception;
	
	public CommandResult() {
		this.type = Type.NONE;
	}
	
	public CommandResult(String message) {
		this.type = Type.NONE;
		ResultItem item = new ResultItem(message);
		this.list.add(item);
	}
	
	public CommandResult(Type type, String message) {
		// keep the type only if it is higher
		if((this.type==null)||(this.type.ordinal()<type.ordinal())){
			this.type = type;
		}
		
		ResultItem item = new ResultItem(type, message);
		this.list.add(item);
	}

	@Override
	public String toString(){
		StringMultiLine str = new StringMultiLine();
		
		for(ResultItem item : this.list){
			str.addLine(item.toString());
		}
		
		return str.toString();
	}
	
	public CommandResult addMessage(String message){
		ResultItem item = new ResultItem(message);
		this.list.add(item);
		return this;
	}
	
	public CommandResult addBlankLine(){
		ResultItem item = new ResultItem("");
		this.list.add(item);
		return this;
	}

	
	public CommandResult addMessage(Type type, String message){
		// keep the type only if it is higher
		if((this.type==null)||(this.type.ordinal()<type.ordinal())){
			this.type = type;
		}
		ResultItem item = new ResultItem(type, message);
		this.list.add(item);
		return this;
	}

	public Type getType(){
		return this.type;
	}
	
	class ResultItem{
		protected Type type;
		protected StringMultiLine message = new StringMultiLine(); 
		protected Throwable throwable;
		
		public ResultItem(String message) {
			// by default it is an info
			this.init(Type.NONE, message);
		}
		
		public ResultItem(Type type, String message) {
			this.init(type, message);
		}

		
		public void init(String message){
			this.message.addLine(message);
		}
		
		public void init(Type type, String message){
			// keep the type only if it is higher
			if((this.type==null)||(this.type.ordinal()<type.ordinal())){
				this.type = type;
			}
			
			this.init(message);
		}

		@Override
		public String toString(){
			String str = null;
			// if type is none just take the string as it is
			if((this.type == Type.NONE)||(this.type == null)){
				str = this.message.toString();
			}else{ // otherwise format the message with the type and application name
				str = "[" + applicationName + "] " +  this.type + " : " + this.message;
			}
			return str;
		}
	}


	public CommandResult concat(CommandResult cr) {
		// if null do nothing excepting the current instance
		if(cr == null){
			return this;
		}
		
		// add all the message
		this.list.addAll(cr.list);
		// change type if higher
		if(cr.getType().ordinal()>this.type.ordinal()){
			this.type = cr.getType();
		}
		return this;
	}

	public CommandResult addErrorMessage(String message) {
		this.addMessage(Type.ERROR, message);
		return this;
	}

	public CommandResult addErrorMessage(String message, Exception exception) {
		this.exception = exception;
		this.addMessage(Type.ERROR, message);
		if(exception != null){
			this.addMessage(ExceptionTools.stackTraceToString(exception));}
		return this;
	}

	
	public CommandResult addWarningMessage(String message) {
		this.addMessage(Type.WARNING, message);
		return this;
	}

	public CommandResult addInfoMessage(String message) {
		this.addMessage(Type.INFO, message);
		return this;
	}
}
