package org.capcaval.lafabrique.factory._sample.generic;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.factory.GenericFactoryWithPool;

public interface Greeter {
	public static GenericFactoryWithPool<Greeter> factory = FactoryTools.newGenericFactoryWithPool(GreeterImpl.class);
	
	String sayHello(String name);
}
