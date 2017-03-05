package org.capcaval.lafabrique.file.command;


public interface FromToCommand<T> extends Command<T>{
	
	public T from(String path);
	public T to(String path);

	
	
}
