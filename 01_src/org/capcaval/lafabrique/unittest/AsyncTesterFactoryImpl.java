package org.capcaval.lafabrique.unittest;

import org.capcaval.lafabrique.unittest.AsyncTester.MultiThreadTesterFactory;

public class AsyncTesterFactoryImpl implements MultiThreadTesterFactory {

	@Override
	public AsyncTester newAsyncTester() {
		return new AsyncTesterImpl();
	}

}
