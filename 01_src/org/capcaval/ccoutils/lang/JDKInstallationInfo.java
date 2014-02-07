package org.capcaval.ccoutils.lang;

import java.nio.file.Path;

public class JDKInstallationInfo {
	public JDKInstallationInfo(Path path) {
		this.path=path;
		this.version= Version.factory.newVersion(path.toFile().getName().replace("jdk", ""));
	}
	public Path path;
	public Version version;
}
