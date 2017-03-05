package org.capcaval.lafabrique.lang.dynamiccaller;


public abstract class DynamicCallerAspect implements DynamicCallerInterface{
	
	protected DynamicCallerInterface originalInstance;

	public DynamicCallerAspect(DynamicCallerInterface originalInstance){
		this.originalInstance = originalInstance;
	}


}
