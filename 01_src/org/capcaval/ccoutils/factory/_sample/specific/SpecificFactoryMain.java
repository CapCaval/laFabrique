package org.capcaval.ccoutils.factory._sample.specific;

import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.factory._sample.specific.Greeter;

public class SpecificFactoryMain {

	public static void main(String[] args) {
		// create a greeter instance with a custom end
		Greeter greeter = Greeter.factory.newGreteer("my old Chap!");
		// just greet
		System.out.println(greeter.sayHelloTo("Roger"));
		
		FactoryTools.setFactoryImplementationInstance(
				Greeter.GreeterFactory.class, 
				new AussieGreeterImpl.AussieGreeterFactoryImpl());
		
		// create a new greeter instance with a custom end
		Greeter ozGreeter = Greeter.factory.newGreteer();
		
		System.out.println(ozGreeter.sayHelloTo("Roger"));
		
	}

}
