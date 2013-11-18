package org.capcaval.ccoutils.formattedtext.formatter;

import org.capcaval.ccoutils.formattedtext.TextFormatter;

public class DefaultTextFormatter implements TextFormatter {

	@Override
	public String format(int columnWidth, String string) {
		//the default do nothing
		return string;
	}
}
