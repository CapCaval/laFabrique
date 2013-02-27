package org.capcaval.cctools.unittest._test;

import junit.framework.Assert;

import org.capcaval.cctools.unittest.AsyncTester;

public class AsyncTesterTest {

	@org.junit.Test
	public void asyncTestNoError(){
		// allocate the AsyncTester
		final AsyncTester asyncTester = AsyncTester.factory.newAsyncTester();
		
		AsyncGreeter greeter = new AsyncGreeter();
		greeter.greet("world", new GreeterCallback() {
			@Override
			public void notifyGreet(String greetStr) {
				asyncTester.finalAssertTrue(greetStr.equals("Hello world"));
			}
		});
		
		asyncTester.waitForAsyncTestToFinish(1000);
		
	}

	@org.junit.Test
	public void asyncTestError() {
		try {
			// allocate the AsyncTester
			final AsyncTester asyncTester = AsyncTester.factory
					.newAsyncTester();

			AsyncGreeter greeter = new AsyncGreeter();
			greeter.greet("world", new GreeterCallback() {
				@Override
				public void notifyGreet(String greetStr) {
					asyncTester.finalAssertTrue(greetStr
							.equals("no Hello to the world!"));
				}
			});

			asyncTester.waitForAsyncTestToFinish(1000);
			Assert.fail("Assert error has not be raised");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.assertTrue("OK, Assert has been raised.", true);
		}

	}

	@org.junit.Test
	public void asyncTestTimeout() {
		try {
			// allocate the AsyncTester
			final AsyncTester asyncTester = AsyncTester.factory
					.newAsyncTester();
			// wait 5s to raise the 1s timeout
			AsyncGreeter greeter = new AsyncGreeter(5000);
			greeter.greet("world", new GreeterCallback() {
				@Override
				public void notifyGreet(String greetStr) {
					asyncTester.finalAssertTrue(greetStr.equals("Hello world"));
				}
			});

			asyncTester.waitForAsyncTestToFinish(1000);
			Assert.fail("Timeout error has not be raised");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.assertTrue("OK, timeout has been raised.", true);
		}
	}

}
