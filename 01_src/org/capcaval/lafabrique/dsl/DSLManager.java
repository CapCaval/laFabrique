package org.capcaval.lafabrique.dsl;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.factory.GenericFactory;

public interface DSLManager {
	public static GenericFactory<DSLManager> factory = FactoryTools.newGenericFactory(DSLManagerImpl.class);

	void registerPlugin(Class<?> pluginImp);

	<T> T loadDSL(Class<T> type);
}
