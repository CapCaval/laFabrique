package org.capcaval.lafabrique.file;

public class SourceInfo {
	
	public enum SourceTypeEnum {interfaceType, classType, enumType, annotationType}

	private String name;
	private String packageName;
	private SourceTypeEnum type;;
	
	SourceInfo(String name, String packageName, SourceTypeEnum type){
		this.name = name;
		this.packageName = packageName;
		this.type = type;
		
	}

	public SourceTypeEnum getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getPackageName() {
		return packageName;
	}
	
	@Override 
	public String toString(){
		return this.packageName + " "  + name + " " + type;
	}
}
