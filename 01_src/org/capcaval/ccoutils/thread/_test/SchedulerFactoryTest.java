package org.capcaval.ccoutils.thread._test;

import java.util.concurrent.ScheduledExecutorService;

import junit.framework.Assert;

import org.capcaval.ccoutils.thread.SchedulerFactory;

public class SchedulerFactoryTest {

	@org.junit.Test
	public void threadWithName(){
		ScheduledExecutorService service = SchedulerFactory.factory.newSingleThreadScheduledExecutor("TestThread");
		service.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello !!!!!!!!!!!!" + Thread.currentThread().getName());
				Assert.assertTrue(false);
			}
		});
		Assert.assertTrue(false);
		
	}
	
}
