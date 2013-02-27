package org.capcaval.cctools.factory._sample.specific;

import org.capcaval.cctools.factory.FactoryTools;
import org.capcaval.cctools.factory._sample.specific.EnglishGreeterImpl.EnglishGreeterFactoryImpl;

public interface Greeter{

	GreeterFactory factory = FactoryTools.newFactory( // associate the factory to the object type with static member
			GreeterFactory.class, // interface of the factory defining a specific signature
			new EnglishGreeterFactoryImpl()); // default factory implementation instance
	
	String sayHelloTo(String name);
	
	// a specific factory with parameters
	public interface GreeterFactory{
		public Greeter newGreteer();
		public Greeter newGreteer(String postMessage); 
		public Greeter newGreteer(String preMessage, String postMessage);
	}
}
