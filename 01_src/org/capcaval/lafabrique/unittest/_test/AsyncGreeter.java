package org.capcaval.lafabrique.unittest._test;

import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.lafabrique.thread.SchedulerFactory;

public class AsyncGreeter {

	
	private ScheduledExecutorService ses;
	protected int waitInMilliSeconds;

	public AsyncGreeter()	{
		this.init(0);
	}
	
	public AsyncGreeter(int waitInMilliSeconds)	{
		this.init(waitInMilliSeconds);
	}
	
	protected void init(int waitInMilliSeconds)	{
		this.waitInMilliSeconds = waitInMilliSeconds;
		this.ses = SchedulerFactory.factory.newSingleThreadScheduledExecutor("Test");
	}
	public void greet(final String name, final GreeterCallback cb){
		this.ses.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(waitInMilliSeconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cb.notifyGreet("Hello "+ name);
			}
		});
	}
}
