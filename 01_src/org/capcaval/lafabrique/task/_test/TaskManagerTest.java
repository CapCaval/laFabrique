package org.capcaval.lafabrique.task._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.task.Task;
import org.capcaval.lafabrique.task.TaskDesc;
import org.capcaval.lafabrique.task.TaskManager;
import org.capcaval.lafabrique.task.TaskTools;
import org.junit.Test;


public class TaskManagerTest {


	private Task<StringBuffer> createTestTask(final String taskValue) {

		Task<StringBuffer> t = new Task<StringBuffer>() {
			@Override
			public CommandResult run(StringBuffer value) {
				CommandResult cr = new CommandResult();
				cr.addMessage(taskValue);
				value.append(taskValue);

				return cr;
			}
		};

		return t;
	}

	@Test
	public void TaskManagerRunPrePostTest() {
		TaskManager<StringBuffer> tm = new TaskManager<>();

		StringBuffer str = new StringBuffer();

		TaskDesc<StringBuffer> t = new TaskDesc<>();
		
		t.preTask(this.createTestTask("a"));
		t.task(this.createTestTask("b"));
		t.postTask(this.createTestTask("c"));

		TaskTools.add(tm, t);
		TaskTools.runAll(tm, str);

		Assert.assertEquals("abc", str.toString());
	}

	@Test
	public void TaskManagerCommandResultTest() {
		TaskManager<StringBuffer> tm = new TaskManager<>();

		StringBuffer str = new StringBuffer();

		TaskDesc<StringBuffer> t1 = new TaskDesc<>();
		TaskDesc<StringBuffer> t2 = new TaskDesc<>();
		TaskDesc<StringBuffer> t3 = new TaskDesc<>();
		
		t1.task = this.createTestTask("a");
		t2.task = this.createTestTask("b");
		t3.task = this.createTestTask("c");

		TaskTools.add(tm, t1, t2, t3);
		CommandResult cr = TaskTools.runAll(tm, str);
		Assert.assertEquals("a\n\nb\n\nc\n", cr.toString());
	}

	
	
	@Test
	public void TaskManagerRunAllAndRemoveTest() {
		TaskManager<StringBuffer> tm = new TaskManager<>();

		StringBuffer str = new StringBuffer();

		TaskDesc<StringBuffer> t1 = new TaskDesc<>();
		TaskDesc<StringBuffer> t2 = new TaskDesc<>();
		TaskDesc<StringBuffer> t3 = new TaskDesc<>();
		
		t1.task = this.createTestTask("a");
		t1.postTask = this.createTestTask("A");
		t2.task = this.createTestTask("b");
		t3.preTask = this.createTestTask("C");
		t3.task = this.createTestTask("c");

		TaskTools.add(tm, t1, t2, t3);
		TaskTools.runAll(tm, str);
		Assert.assertEquals("aAbCc", str.toString());

		TaskTools.removeTaskDesc(tm, t2);
		StringBuffer str2 = new StringBuffer();
		TaskTools.runAll(tm, str2);
		Assert.assertEquals("aACc", str2.toString());
	}
	
	@Test
	public void TaskToolsTest() {
		
		TaskDesc<StringBuffer> desc = new TaskDesc<>();
		desc.preTask = this.createTestTask("1");
		desc.task = this.createTestTask("2");
		desc.postTask = this.createTestTask("3");
		
		StringBuffer str = new StringBuffer();
		CommandResult cr = TaskTools.runTask(desc, str);

		Assert.assertEquals("1\n2\n3", cr.toString());
		Assert.assertEquals("123", str.toString());
	}

}
