package org.capcaval.ccoutils.unittest;

import org.capcaval.ccoutils.unittest.AsyncTester.MultiThreadTesterFactory;

public class AsyncTesterFactoryImpl implements MultiThreadTesterFactory {

	@Override
	public AsyncTester newAsyncTester() {
		return new AsyncTesterImpl();
	}

}
