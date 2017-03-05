package org.capcaval.lafabrique.dsl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GenericDSLObject {
	Map<String, Object>map = new HashMap<>();
	
	GenericDSLObject(Class<?> type){
		for(Method method : type.getDeclaredMethods()){
			if(method.getName().startsWith("get")){
				//map.put(key, value)
			}
		}
		
	}
	
	
}
