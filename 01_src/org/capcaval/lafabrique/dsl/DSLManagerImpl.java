package org.capcaval.lafabrique.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSLManagerImpl implements DSLManager{
	List<Class<?>> pluginImplList = new ArrayList<>();
	Map<Class<?>, Class<?>> map = new HashMap<>();
	
	@Override
	public void registerPlugin(Class<?> pluginImpType) {
		this.pluginImplList.add(pluginImpType);
		
		Class<?>dslType = pluginImpType.getInterfaces()[0];
		
		// add ref with interface as key
		this.map.put(dslType, pluginImpType);
	}

	@Override
	public <T> T loadDSL(Class<T> type) {
		// first 
		
		return null;
	}

}
