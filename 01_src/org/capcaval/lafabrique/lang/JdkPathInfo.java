package org.capcaval.lafabrique.lang;

import java.nio.file.Path;

public class JdkPathInfo {
	public JdkPathInfo(Path path) {
		this.path=path;
		this.version= Version.factory.newVersion(path.toFile().getName().replace("jdk", ""));
	}
	public Path path;
	public Version version;
}
