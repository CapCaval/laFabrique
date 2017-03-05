package org.capcaval.lafabrique.factory._test;

import org.junit.Assert;

public class FactoryTest {

	@org.junit.Test
	public void newInstanceTest(){
		//use the default implementation class 
		Dummy dummy = Dummy.factory.newInstance();
		String hello = dummy.hello();
		System.out.println(hello);
		Assert.assertEquals("Hello from DummyImpl.class", hello);

		// use another implementation
		Dummy.factory.setObjectImplementationType(DummyAnotherImpl.class);
		dummy = Dummy.factory.newInstance();
		hello = dummy.hello();
		System.out.println(hello);
		Assert.assertEquals("hello from DummyAnotherImpl.class", hello);
	}

}
