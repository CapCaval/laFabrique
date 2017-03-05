package org.capcaval.lafabrique.task;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.lang.ArrayTools;

public class TaskTools {

	public static <T> CommandResult runTask(TaskDesc<T> desc, T value) {
		CommandResult cr = new CommandResult();
		// if any execute the pre-task
		if(desc.preTask != null){
			CommandResult crt = desc.preTask.run(value);
			cr.concat(crt);
		}
		
		// if any execute the task
		if(desc.task != null){
			CommandResult crt = desc.task.run(value);
			cr.concat(crt);
		}
		
		// if any execute the post-task
		if(desc.postTask != null){
			CommandResult crt = desc.postTask.run(value);
			cr.concat(crt);
		}
		
		return cr;
	}
	
	public static <T>void add(TaskManager<T> tm, TaskDesc<T>... taskDescArray) {		
		for(TaskDesc<T> taskDesc : taskDescArray){
			tm.taskArray = ArrayTools.add(taskDesc, tm.taskArray);
		}
	}

	public static <T> CommandResult runAll(TaskManager<T> tm, T value) {
		CommandResult cr = new CommandResult();
		
		if(tm == null){
			// in fact do nothing
			return cr;
		}

		// for all run them and concat result string
		for(TaskDesc<T> desc : tm.taskArray){
			// concat task comments to the global one
			cr.concat(TaskTools.runTask(desc, value));
			// for a cleaner presentation add a blank line
			cr.addBlankLine();
			
			if(cr.getType() == Type.ERROR){
				// do not finish if get an error
				break;
			}
		}
		return cr;
	}

	public static <T> void removeTaskDesc(TaskManager<T> tm, TaskDesc<T> taskDesc){
		// remove it
		tm.taskArray = ArrayTools.removeElement(taskDesc, tm.taskArray);
	}
}
