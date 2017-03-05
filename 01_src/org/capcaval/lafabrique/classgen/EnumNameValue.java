package org.capcaval.lafabrique.classgen;

public class EnumNameValue {

	protected String name;

	public EnumNameValue(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}

}
