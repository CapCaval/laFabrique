package org.capcaval.lafabrique.lafab;

import java.util.concurrent.atomic.AtomicReference;

public class CurrentConfig extends Config {
	
	protected static AtomicReference<Config> currentConfig = new AtomicReference<Config>(new CurrentConfig());
	
	@Override
	public void defineProject() {
		// do nothing
	}

	public static Config getConfig() {
		return CurrentConfig.currentConfig.get();
	}
}
