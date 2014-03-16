package org.capcaval.cccoutils.lang.proxy._test;

import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.cccoutils.lang.proxy.ProxyTools;
import org.capcaval.ccoutils.thread.SchedulerFactory;
import org.capcaval.ccoutils.unittest.AsyncTester;

public class ProxyThreadInvocationHandlerTest {

	@org.junit.Test
	public void proxyThreadTest(){
		final AsyncTester asyncTester = AsyncTester.factory.newInstance();
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				asyncTester.finalAssertTrue(Thread.currentThread().getName().equals("TestThread"));
			}
		};
		
		ScheduledExecutorService ses = SchedulerFactory.factory.newSingleThreadScheduledExecutor("TestThread");
		Runnable proxy = ProxyTools.newThreadProxy(Runnable.class, r, ses);
		proxy.run();
		
		asyncTester.waitForAsyncTestToFinish(500);
	}
}
