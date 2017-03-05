package org.capcaval.lafabrique.file.pathfilter;

import java.nio.file.Path;

public class RemovePathFinishingWith implements PathFilter {

	private String[] excludeStringArray;

	public RemovePathFinishingWith(String... excludeStringArray) {
		this.excludeStringArray = excludeStringArray;
	}

	@Override
	public boolean isPathValid(Path path) {
		boolean isValid = true;

		for (String str : this.excludeStringArray) {
			if (path.toFile().getName().endsWith(str)) {
				isValid = false;
				// bye bye
				break;
			}
		}
		return isValid;
	}

}
