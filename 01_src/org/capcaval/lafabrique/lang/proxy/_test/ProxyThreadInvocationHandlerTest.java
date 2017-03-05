package org.capcaval.lafabrique.lang.proxy._test;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.lafabrique.lang.proxy.ProxyThreadInvocationHandler.ParamStrategy;
import org.capcaval.lafabrique.lang.proxy.ProxyTools;
import org.capcaval.lafabrique.thread.SchedulerFactory;
import org.capcaval.lafabrique.unittest.AsyncTester;

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

	static public class ParamClass{
		public String strValue;
		public int intValue;
		
		public ParamClass(){};
	}
	
	public interface TestProxyInterface{
		public void setValue(ParamClass value);
	}
	
	@org.junit.Test
	public void proxyThreadTestAndClone(){
		final AsyncTester asyncTester = AsyncTester.factory.newInstance();
		ParamClass resultReference = new ParamClass();
		resultReference.intValue = 114;
		resultReference.strValue = "Hello!";
		
		TestProxyInterface r = new TestProxyInterface() {
			@Override
			public void setValue(ParamClass value) {
				System.out.println("#########   " + resultReference.hashCode() + " " + value.hashCode() + " " + value);
				System.out.println( resultReference.strValue.hashCode() + " " + value.strValue.hashCode() + " " + value.strValue );
				asyncTester.assertTrue(value!=resultReference);
				asyncTester.assertTrue(value.intValue == resultReference.intValue);
				asyncTester.assertTrue(value.strValue.equals(resultReference.strValue));
				asyncTester.finalAssertTrue(Thread.currentThread().getName().equals("TestThread"));
			}
		};
		
		ScheduledExecutorService ses = SchedulerFactory.factory.newSingleThreadScheduledExecutor("TestThread");
		TestProxyInterface proxy = ProxyTools.newThreadProxy(TestProxyInterface.class, r, ses, ParamStrategy.cloneParam);
		proxy.setValue(resultReference);
		
		asyncTester.waitForAsyncTestToFinish(100);
	}
}
