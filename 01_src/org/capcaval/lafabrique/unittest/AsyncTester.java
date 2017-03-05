package org.capcaval.lafabrique.unittest;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.factory.GenericFactory;

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
	public void assertTrueAtXIteration(int i, boolean isEqual);
	public void finalAssertTrueAtXIteration(int i, boolean isEqual);
}