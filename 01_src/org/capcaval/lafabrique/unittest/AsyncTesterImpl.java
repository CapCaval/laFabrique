package org.capcaval.lafabrique.unittest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AsyncTesterImpl implements AsyncTester {
	AtomicBoolean isTestOver = new AtomicBoolean(false);

	AtomicReference<TestStatus> testStatus;
	AtomicReference<StackTraceElement[]> stackList;
	AtomicReference<AssertionError> exception;
	AtomicInteger counter =new AtomicInteger(0);
	AtomicInteger xtimes = new AtomicInteger(0);

	public AsyncTesterImpl() {
		this.testStatus = new AtomicReference<TestStatus>(
				TestStatus.ok_test_passed);
		this.stackList = new AtomicReference<StackTraceElement[]>();
		this.exception = new AtomicReference<AssertionError>();
	}

	@Override
	public void assertTrue(boolean value) {
		if (value == false) {
			System.out.println("Assert false" + value);
			// get the current stack
			this.stackList.set(Thread.currentThread().getStackTrace());
			this.exception.set(new AssertionError(
					"expected true but assertion is " + value));
			testStatus.set(TestStatus.fail_assertfalse);
			
			// just finish it
			this.isTestOver.set(true);
		}else{System.out.println("Value = true " + value);}
	}

	@Override
	public void finalAssertTrue(boolean value) {
		this.assertTrue(value);
		// just finish it
		this.isTestOver.set(true);

	}

	@Override
	public void finalTest() {
		// just finish it
		this.isTestOver.set(true);
	}

	@Override
	public void waitForAsyncTestToFinish(final long timeOutInMillisec) {
		long startTime = System.currentTimeMillis();
		while(isTestOver.get() == false) {

			long stopTime = System.currentTimeMillis();
			if (stopTime - startTime > timeOutInMillisec) {
				isTestOver.set(true);
				testStatus.set(TestStatus.fail_timeout);
				
				int xTimesPerformed = this.counter.get();
				int xtimesToBeDone = this.xtimes.get();
				String errorMessage = null;
				
				if(xTimesPerformed<xtimesToBeDone){
					errorMessage = "[AsyncTester.CapCaval.org ERROR] :"+
							"\nTest is expected to be passed " + xtimesToBeDone + " times and it has been done " + xTimesPerformed + " times" + 
							"\nCheck that the timeout is not too small and that the final assert is performed " + xtimesToBeDone + " time." +
							"\n X times : " + this.counter.get() + "/" +this.xtimes.get();
				}else{
					errorMessage = "[AsyncTester.CapCaval.org ERROR] :"+ 
							"\nTest is Expected to be finished before the following timeout value :" + timeOutInMillisec + "msec" + 
							"\nCheck that the timeout is not too small and that the final assert is performed in time." +
							"\n X times : " + this.counter.get() + "/" +this.xtimes.get();
				}
				this.exception.set(new AssertionError(errorMessage));
			}
			// release the cpu if needed
			Thread.yield();
		}
		if (this.testStatus.get() == TestStatus.fail_assertfalse) {
			AssertionError ex = new AssertionError();
			ex.setStackTrace(this.stackList.get());
			throw this.exception.get();
		} else if (this.testStatus.get() == TestStatus.fail_timeout) {
			// get the current stack
			this.stackList.set(Thread.currentThread().getStackTrace());

			AssertionError ex = new AssertionError();
			ex.setStackTrace(this.stackList.get());
			throw this.exception.get();
		}
	}

	@Override
	public void finalAssertTrueXTimes(int xTimes, boolean value) {
		this.xtimes.set(xTimes);
		
		// assert the current value
		this.assertTrue(value);
		
		// increment the counter
		int counter = this.counter.incrementAndGet();
		
		// check if this the last assert to be performed
		if(counter >= xTimes){
			this.finalAssertTrue(value);
		}

	}

	@Override
	public void assertTrueAtXIteration(int i, boolean isEqual) {
		// apply the test if it is the corresponding N interation
		if(this.counter.get() == i){
			this.assertTrue(isEqual);
		}
	}

	@Override
	public void finalAssertTrueAtXIteration(int i, boolean isEqual) {
		// check if this the last assert to be performed
		if( counter.get() >= i){
			this.finalAssertTrue(isEqual);
		}
		
		// increment the counter
		this.counter.incrementAndGet();
	}

}