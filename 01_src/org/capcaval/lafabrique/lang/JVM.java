package org.capcaval.lafabrique.lang;

import java.nio.file.Path;

public class JVM {
	public enum JVMTypeEnum {JDK, JRE}
	
	protected JVMTypeEnum jvmType;
	protected String name;
	protected Path path;
	protected Version version;
	
	public JVM(Path path, String name, Version version, JVMTypeEnum type){
		this.path = path;
		this.name = name;
		this.version = version;
		this.jvmType = type;
	}

	public JVMTypeEnum getJvmType() {
		return jvmType;
	}

	public String getName() {
		return name;
	}

	public Path getPath() {
		return path;
	}

	public Version getVersion() {
		return version;
	}
	
	public String toString(){
		return this.name + " " + this.path.toString();
	}
	
}
