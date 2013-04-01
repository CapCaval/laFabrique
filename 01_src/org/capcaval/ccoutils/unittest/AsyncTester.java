package org.capcaval.ccoutils.unittest;

import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.factory.GenericFactory;

public interface AsyncTester {
	static GenericFactory<AsyncTester> factory = FactoryTools.newGenericFactory(AsyncTesterImpl.class); 
	
	public void assertTrue(boolean value);
	public void finalAssertTrue(boolean value);
	public void finalAssertTrueXTimes(int xTime, boolean value);
	public void finalTest();
	public void waitForAsyncTestToFinish(long timeOutInMillisec);
	public interface MultiThreadTesterFactory {
		AsyncTester newAsyncTester();
	}
}