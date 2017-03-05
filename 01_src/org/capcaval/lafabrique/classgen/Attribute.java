package org.capcaval.lafabrique.classgen;

public class Attribute<T> {
	protected Class<?> type;
	protected String name;
	protected T value;
	
	public Attribute(String name, Class<?> type, T value){
		this.type = type;
		this.value = value;
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}
}
