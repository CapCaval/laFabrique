package org.capcaval.cctools.unittest;

import org.capcaval.cctools.unittest.AsyncTester.MultiThreadTesterFactory;

public class AsyncTesterFactoryImpl implements MultiThreadTesterFactory {

	@Override
	public AsyncTester newAsyncTester() {
		return new AsyncTesterImpl();
	}

}
