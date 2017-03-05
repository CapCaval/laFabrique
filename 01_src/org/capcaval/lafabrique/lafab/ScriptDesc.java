package org.capcaval.lafabrique.lafab;

public class ScriptDesc {
	public String getName() {
		return name;
	}

//	public Class<?> getClassToExecute() {
//		return classToExecute;
//	}

	public String getClassToExecute() {
		return classToExecute;
	}

	
	protected String name;
	//protected Class<?> classToExecute;
	protected String classToExecute;

	public ScriptDesc(String name, Class<?> classToExecute) {
		this.init(name, classToExecute.getCanonicalName());
	}

	public ScriptDesc(String name, String classToExecute) {
		this.init(name, classToExecute);
	}
	
	public void init(String name, String classToExecute) {
		this.name = name;
		this.classToExecute = classToExecute;
	}

}
