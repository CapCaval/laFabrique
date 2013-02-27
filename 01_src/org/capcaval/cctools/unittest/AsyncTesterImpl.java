package org.capcaval.cctools.unittest;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;

public class AsyncTesterImpl implements AsyncTester {
	AtomicBoolean isTestOver = new AtomicBoolean(false);
	
	AtomicReference<TestStatus>testStatus;
	AtomicReference<StackTraceElement[]>stackList;
	AtomicReference<AssertionError>exception;
	
	
	public AsyncTesterImpl(){
		this.testStatus = new AtomicReference<TestStatus>(TestStatus.ok_test_passed);
		this.stackList = new AtomicReference<StackTraceElement[]>();
		this.exception = new AtomicReference<AssertionError>();
	}
	
	@Override
	public void assertTrue(boolean value) {
		if(value == false){
			//get the current stack
			this.stackList.set(Thread.currentThread().getStackTrace());
			this.exception.set(new AssertionError("expected true but assertion is " + value));
			// just finish it
			this.isTestOver.set(true);
			testStatus.set(TestStatus.fail_assertfalse);
		}
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
	public void waitForAsyncTestToFinish(long timeOutInMillisec) {
		long startTime = System.currentTimeMillis();
		while(isTestOver.get() == false){
			
			long stopTime = System.currentTimeMillis();
			if(stopTime-startTime > timeOutInMillisec){
				isTestOver.set(true);
				testStatus.set(TestStatus.fail_timeout);
				this.exception.set(new AssertionError("Expected test finish before the following timeut value :" + timeOutInMillisec + "msec"));
			}
				
		}
		
		if(this.testStatus.get()==TestStatus.fail_assertfalse){
			//Assert.assertTrue(Arrays.toString(this.stackList.get()), false);
			AssertionError ex = new AssertionError();
			ex.setStackTrace(this.stackList.get());
			throw this.exception.get();
		}
		else if(this.testStatus.get()==TestStatus.fail_timeout){
			//Assert.assertTrue(Arrays.toString(this.stackList.get()), false);
			AssertionError ex = new AssertionError();
			ex.setStackTrace(this.stackList.get());
			throw this.exception.get();
		}
		
	}

}
