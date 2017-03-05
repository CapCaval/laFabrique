package org.capcaval.lafabrique.file.pathfilter;

import java.nio.file.Path;

public interface PathFilter {
	boolean isPathValid(Path path);
}
