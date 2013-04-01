package org.capcaval.ccoutils.factory_old._test;

import org.capcaval.ccoutils.factory_old.FactoryInjector;

public interface StringFactory {
	@FactoryInjector
	public static StringFactory factory = new StringFactoryImpl();
	
	public String newString(String str);
}
