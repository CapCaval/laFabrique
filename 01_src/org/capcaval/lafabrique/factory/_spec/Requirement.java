package org.capcaval.lafabrique.factory._spec;

public abstract class Requirement {
	public int id;
	public String name;
	public String shortDescription;
	public String description;
	
	abstract void define();
	
}
