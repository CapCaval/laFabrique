package org.capcaval.lafabrique.task;

public class TaskWrapper <T>{
	protected Task<T> task;
	protected Task<T> preTask = null;
	protected Task<T> postTask = null;

	public TaskWrapper(Task<T> task) {
		this.task = task;
	}
}
