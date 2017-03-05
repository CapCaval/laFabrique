package org.capcaval.lafabrique.task;


public class TaskDesc<T> {
	public Task<T> task;
	public Task<T> preTask;
	public Task<T> postTask;

	public void task(Task<T> task){
		this.task = task;
	}
	
	public void preTask(Task<T> preTask){
		this.preTask = preTask;
	}

	public void postTask(Task<T> postTask){
		this.postTask = postTask;
	}
}
