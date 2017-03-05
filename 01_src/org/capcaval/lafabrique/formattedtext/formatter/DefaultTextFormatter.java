package org.capcaval.lafabrique.formattedtext.formatter;

import org.capcaval.lafabrique.formattedtext.TextFormatter;

public class DefaultTextFormatter implements TextFormatter {

	@Override
	public String format(int columnWidth, String string) {
		//the default do nothing
		return string;
	}
}
