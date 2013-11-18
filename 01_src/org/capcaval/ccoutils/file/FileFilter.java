package org.capcaval.ccoutils.file;

import java.nio.file.Path;

public interface FileFilter {
	boolean isFileValid(Path path);
}
