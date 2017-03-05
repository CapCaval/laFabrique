package org.capcaval.lafabrique.dsl._test2;

public abstract class AbstractCoolDSLImpl implements CoolDSL {

	private String name;
	
	@Override
	public void defineName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}

}
