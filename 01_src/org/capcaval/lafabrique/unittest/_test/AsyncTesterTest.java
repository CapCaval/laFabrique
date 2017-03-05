package org.capcaval.lafabrique.unittest._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.unittest.AsyncTester;

public class AsyncTesterTest {

	@org.junit.Test
	public void asyncTestNoError(){
		// allocate the AsyncTester
		final AsyncTester asyncTester = AsyncTester.factory.newInstance();
		
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
					.newInstance();

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
	public void asyncXIterationTest() {
		try {
			// allocate the AsyncTester
			final AsyncTester asyncTester = AsyncTester.factory.newInstance();
			
			GreeterCallback cb =new GreeterCallback() {
				@Override
				public void notifyGreet(String greetStr) {
					asyncTester.assertTrueAtXIteration(0, "Hello first".equals(greetStr));				
					asyncTester.assertTrueAtXIteration(1, "Hello second".equals(greetStr));
					asyncTester.finalAssertTrueAtXIteration(2, greetStr.equals("Hello final!"));
				}
			};
			
			AsyncGreeter greeter = new AsyncGreeter();
			greeter.greet("first", cb);
			greeter.greet("second", cb);
			greeter.greet("final!", cb); 

			asyncTester.waitForAsyncTestToFinish(1000);
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.assertTrue("No exception shall be raised", false);
		}
	}
	
	
	@org.junit.Test
	public void asyncXIterationErrorLastTest() {
		try {
			// allocate the AsyncTester
			final AsyncTester asyncTester = AsyncTester.factory.newInstance();
			
			GreeterCallback cb =new GreeterCallback() {
				@Override
				public void notifyGreet(String greetStr) {
					asyncTester.assertTrueAtXIteration(0, "Hello first".equals(greetStr));				
					asyncTester.assertTrueAtXIteration(1, "Hello second".equals(greetStr));
					asyncTester.finalAssertTrueAtXIteration(2, greetStr.equals("Raise Error"));
				}
			};
			
			AsyncGreeter greeter = new AsyncGreeter();
			greeter.greet("first", cb);
			greeter.greet("second", cb);
			greeter.greet("final!", cb); 

			asyncTester.waitForAsyncTestToFinish(1000);
			Assert.fail("Assert error has not be raised");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.assertTrue("OK, Assert has been raised.", true);
		}
	}
	
		@org.junit.Test
	public void asyncXIterationErrorNotLastTest() {
		try {
			// allocate the AsyncTester
			final AsyncTester asyncTester = AsyncTester.factory.newInstance();
			
			GreeterCallback cb =new GreeterCallback() {
				@Override
				public void notifyGreet(String greetStr) {
					asyncTester.assertTrueAtXIteration(0, "Hello first".equals(greetStr));				
					asyncTester.assertTrueAtXIteration(1, "Raise Error".equals(greetStr));
					asyncTester.finalAssertTrueAtXIteration(2, greetStr.equals("Hello final!"));
				}
			};
			
			AsyncGreeter greeter = new AsyncGreeter();
			greeter.greet("first", cb);
			greeter.greet("second", cb);
			greeter.greet("final!", cb); 

			asyncTester.waitForAsyncTestToFinish(1000);

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
					.newInstance();
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
