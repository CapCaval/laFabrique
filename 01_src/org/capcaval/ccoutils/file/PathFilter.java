package org.capcaval.ccoutils.file;

import java.nio.file.Path;

public interface PathFilter {
	boolean isPathValid(Path path);
}
