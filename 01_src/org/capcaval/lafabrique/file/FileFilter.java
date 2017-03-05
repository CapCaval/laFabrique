package org.capcaval.lafabrique.file;

import java.io.File;

public interface FileFilter {
	boolean isFileValid(File path);
}
