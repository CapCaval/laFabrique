package org.capcaval.lafabrique.file.pathfilter;

import java.nio.file.Path;

public class IncludePathFinishingWith implements PathFilter {

	private String[] excludeStringArray;

	public IncludePathFinishingWith(String... excludeStringArray) {
		this.excludeStringArray = excludeStringArray;
	}

	@Override
	public boolean isPathValid(Path path) {
		boolean isValid = false;

		for (String str : this.excludeStringArray) {
			if (path.toFile().getName().endsWith(str)) {
				isValid = true;
				// bye bye
				break;
			}
		}
		return isValid;
	}

}
