package org.capcaval.lafabrique.lang;

import java.nio.file.Path;

import javax.tools.JavaCompiler;

public class Jdk {
	
	protected JavaCompiler compiler;
	protected JdkPathInfo pathInfo;
	protected String variable;
	
	
	protected Jdk(Path path, JavaCompiler compiler, String variable) {
		this.pathInfo = new JdkPathInfo(path);
		this.compiler = compiler;
		this.variable = variable;
	}
	
	Version getVersion(){
		return this.pathInfo.version;
	}
}
