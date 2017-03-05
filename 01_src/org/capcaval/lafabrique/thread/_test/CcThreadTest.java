package org.capcaval.lafabrique.thread._test;

import org.capcaval.lafabrique.thread.CcThread;
import org.capcaval.lafabrique.unittest.AsyncTester;

public class CcThreadTest {

	@org.junit.Test
	public void runTest() throws InterruptedException{
		final AsyncTester asyncTester = AsyncTester.factory.newInstance();
		
		CcThread t = new CcThread("Toto", new Runnable(){
			@Override
			public void run() {
				
				asyncTester.assertTrue(Thread.currentThread().getName().equals("Toto"));
				System.out.println("Hello ! " + Thread.currentThread().getName());
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}});
		
		t.subscribeThreadStateEvent(new ThreadStateEvent(){
			@Override
			public void notifyThreadStarted() {
				System.out.println("Thread started : " + Thread.currentThread().getName());
				asyncTester.assertTrue(Thread.currentThread().getName().equals("Toto"));
			}

			@Override
			public void notifyThreadStopped() {
				System.out.println("Thread stopped : " + Thread.currentThread().getName());
				asyncTester.finalAssertTrue(Thread.currentThread().getName().equals("Toto"));
			}
			
		});
		
		t.start();
		Thread.sleep(500);
		t.stop();

		t.start();
		Thread.sleep(500);
		t.stop();
		
		asyncTester.waitForAsyncTestToFinish(1000);
//
//		t.start();
//		Thread.sleep(1000);
//		t.stop();
		
	}
	
}
