package org.capcaval.ccoutils.factory._sample.generic;

import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.factory.GenericFactory;
import org.capcaval.ccoutils.factory._impl.GenericFactoryImpl;

public interface Greeter {
	public static GenericFactory<Greeter> factory = FactoryTools.newGenericFactory(GreeterImpl.class);
	
	String sayHello(String name);
}
