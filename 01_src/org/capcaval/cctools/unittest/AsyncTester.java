package org.capcaval.cctools.unittest;

public interface AsyncTester {
	static MultiThreadTesterFactory factory = new AsyncTesterFactoryImpl();
	public void assertTrue(boolean value);
	public void finalAssertTrue(boolean value);
	public void finalAssertTrueXTimes(int xTime, boolean value);
	public void finalTest();
	public void waitForAsyncTestToFinish(long timeOutInMillisec);
	public interface MultiThreadTesterFactory {
		AsyncTester newAsyncTester();
	}
}