package org.capcaval.ccoutils.file.command;

import java.util.ArrayList;
import java.util.List;

public abstract class FromToCommandImpl<T> extends CommandImpl<T> implements FromToCommand<T>{
	List<String> from = new ArrayList<String>();
	List<String> to = new ArrayList<String>();
}
