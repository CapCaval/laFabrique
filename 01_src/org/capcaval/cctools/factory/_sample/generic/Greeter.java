package org.capcaval.cctools.factory._sample.generic;

import org.capcaval.cctools.factory.FactoryTools;
import org.capcaval.cctools.factory.GenericFactory;
import org.capcaval.cctools.factory._impl.GenericFactoryImpl;

public interface Greeter {
	public static GenericFactory<Greeter> factory = FactoryTools.newGenericFactory(GreeterImpl.class);
	
	String sayHello(String name);
}
