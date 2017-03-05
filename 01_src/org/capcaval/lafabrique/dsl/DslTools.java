package org.capcaval.lafabrique.dsl;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.dsl._test.plugins.HelloWorldPlugInImpl;
import org.capcaval.lafabrique.lang.ListTools;

public class DslTools {

	protected static List<Class<?>> dslArray = new ArrayList<>();
	
	public static void registerDsl(Class<?>... typeArray) {
		ListTools.concat(dslArray, typeArray);
		
	}

	public <T> T createDSLFromInterface(Class<T> type){
		T instance = null;
		try {
			instance = type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return instance;
	}
}
