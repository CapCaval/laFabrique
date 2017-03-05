package org.capcaval.lafabrique.task;


public class TaskManager <T>{

	TaskDesc<T>[] taskArray = null;
	
	public void taskArray(TaskDesc<T>... taskArray){
		this.taskArray = taskArray;
	}
}
