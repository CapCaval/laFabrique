package org.capcaval.ccoutils.file.command;

import java.util.ArrayList;
import java.util.List;

public interface FromToCommand<T> extends Command<T>{
	
	public T from(String path);
	public T to(String path);

	
	
}
