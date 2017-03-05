package org.capcaval.lafabrique.factory._test;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.factory.GenericFactory;

public interface Dummy {

	public static GenericFactory<Dummy> factory = FactoryTools.newGenericFactory(DummyImpl.class);
	
	public String hello();
			
}
