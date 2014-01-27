package org.capcaval.ccoutils.file;

import java.io.File;

public interface FileFilter {
	boolean isFileValid(File path);
}
